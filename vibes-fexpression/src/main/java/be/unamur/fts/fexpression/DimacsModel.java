package be.unamur.fts.fexpression;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static be.unamur.fts.fexpression.Feature.feature;
import be.unamur.fts.fexpression.exception.DimacsFormatException;

import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import static com.google.common.base.Preconditions.*;

public class DimacsModel {

    private static final Logger logger = LoggerFactory.getLogger(DimacsModel.class);

    public static DimacsModel createFromTvlParserMappingFile(File mapping) throws IOException {
        return new DimacsModel(mapping, null);
    }

    public static DimacsModel createFromDimacsFile(String dimacsFilePath) throws IOException {
        File dimacs = new File(dimacsFilePath);
        return createFromDimacsFile(dimacs);
    }

    public static DimacsModel createFromDimacsFile(File dimacs) throws IOException {
        checkArgument(dimacs.isFile(), "File %s not found!", dimacs);
        return new DimacsModel(dimacs);
    }

    public static DimacsModel createFromTvlParserGeneratedFiles(String mappingFilePath, String dimacsFilePath) throws IOException {
        File dimacs = new File(dimacsFilePath);
        File mapping = new File(mappingFilePath);
        return createFromTvlParserGeneratedFiles(mapping, dimacs);
    }

    public static DimacsModel createFromTvlParserGeneratedFiles(File mapping, File dimacs) throws IOException {
        return new DimacsModel(mapping, dimacs);
    }

    public static DimacsModel createFromFeatureList(List<String> features) {
        return new DimacsModel(features);
    }

    public static DimacsModel createFromFeatureList(FExpression fd) throws DimacsFormatException {
        return new DimacsModel(fd);
    }

    private ArrayList<int[]> dimacsFD;
    private BiMap<String, Integer> featureMapping;
    private FExpression fd;

    private DimacsModel(FExpression fd) throws DimacsFormatException {
        this.fd = fd.toCnf();
        featureMapping = HashBiMap.create();
        int i = 1;
        for (Feature f : fd.getFeatures()) {
            featureMapping.put(f.getName(), i);
            i++;
        }
        dimacsFD = Lists.newArrayList();
        int[][] cnf = DimacsFormatter.format(this.fd, featureMapping);
        for (i = 0; i < cnf.length; i++) {
            dimacsFD.add(cnf[i]);
        }
    }

    private DimacsModel(List<String> features) {
        featureMapping = HashBiMap.create();
        int i = 1;
        for (String f : features) {
            featureMapping.put(f, i);
            i++;
        }
        dimacsFD = Lists.newArrayList();
        // FExpression building
        buildFDFromFile();
    }

    private DimacsModel(File dimacs) throws IOException {
        // Read mapping 
        logger.debug("Read mapping");
        featureMapping = HashBiMap.create();
        String[] tabLine;
        int nbrClauses = 0;
        int[] clause;
        dimacsFD = Lists.newArrayList();
        for (String line : Splitter.on(CharMatcher.anyOf("\n\r")).omitEmptyStrings()
                .split(Files.toString(dimacs, Charsets.UTF_8))) {
            logger.trace("line = " + line);
            tabLine = line.split(" ");
            if (tabLine.length == 0 || tabLine[0].equals("p")) {
                logger.debug("Skip line '{}' in dimacs file {}",
                        Joiner.on(' ').join(tabLine), dimacs.getPath());
            } else if (tabLine[0].equals("c")) {
                featureMapping.put(tabLine[2], Integer.valueOf(tabLine[1]));
            } else {
                clause = new int[tabLine.length - 1]; // last char is a 0 in
                // dimacs format
                for (int i = 0; i < clause.length; i++) {
                    clause[i] = Integer.parseInt(tabLine[i]);
                }
                this.dimacsFD.add(clause);
                nbrClauses++;
            }
        }
        logger.debug("{} clauses added to the solver", nbrClauses);
        // FExpression building
        buildFDFromFile();
    }

    private DimacsModel(File mapping, File dimacs) throws IOException {
        checkArgument(mapping.exists() && mapping.isFile(), "Mapping file %s not found!", mapping);
        checkArgument((dimacs == null) || (dimacs.exists() && dimacs.isFile()), "Dimacs file %s not found!", dimacs);
        // Read mapping 
        logger.debug("Read mapping");
        featureMapping = HashBiMap.create();
        String[] tabLine;
        for (String line : Splitter.on(CharMatcher.anyOf("\n\r")).omitEmptyStrings()
                .split(Files.toString(mapping, Charsets.UTF_8))) {
            logger.trace("line = " + line);
            tabLine = line.split(" ");
            if (tabLine.length >= 1 || tabLine.length <= 2) {
                featureMapping.put(
                        tabLine.length > 1 ? tabLine[1] : Integer.toString(featureMapping
                                        .size() + 1), Integer.valueOf(tabLine[0]));
            } else {
                logger.info("Skipping line {} in feature id mapping", line);
            }
        }
        // Read DIMACS (if there is one)
        dimacsFD = Lists.newArrayList();
        if (dimacs != null) {
            logger.debug("Read DIMACS");
            int nbrClauses = 0;
            int[] clause;
            for (String line : Splitter.on(CharMatcher.anyOf("\n\r")).omitEmptyStrings()
                    .split(Files.toString(dimacs, Charsets.UTF_8))) {
                logger.trace("line = " + line);
                tabLine = line.split(" ");
                logger.trace("tabLine = {}", Arrays.toString(tabLine));
                if (tabLine.length == 0 || tabLine[0].equals("c") || tabLine[0].equals("p")) {
                    logger.debug("Skip line '{}' in dimacs file {}",
                            Joiner.on(' ').join(tabLine), dimacs.getPath());
                } else {
                    clause = new int[tabLine.length - 1]; // last char is a 0 in
                    // dimacs format
                    for (int i = 0; i < clause.length; i++) {
                        clause[i] = Integer.parseInt(tabLine[i]);
                    }
                    this.dimacsFD.add(clause);
                    nbrClauses++;
                }
            }
            logger.debug("{} clauses added to the solver", nbrClauses);
        } else {
            logger.debug("No DIMACS provided, FD will be 'true'");
        }
        // FExpression building
        buildFDFromFile();
    }

    private void buildFDFromFile() {
        fd = FExpression.trueValue();
        for (int[] tab : dimacsFD) {
            if (tab.length > 0) {
                FExpression disj = null;
                for (int f : tab) {
                    Feature feat;
                    if (f > 0) {
                        feat = feature(featureMapping.inverse().get(f));
                        if (disj == null) {
                            disj = new FExpression(feat);
                        } else {
                            disj.orWith(new FExpression(feat));
                        }
                    } else if (f < 0) {
                        feat = feature(featureMapping.inverse().get(-f));
                        if (disj == null) {
                            disj = new FExpression(feat);
                            disj.notWith();
                        } else {
                            disj.orWith(new FExpression(feat).not());
                        }
                    } else {
                        logger.trace("End of DIMACS line reached");
                    }
                }
                fd.andWith(disj);
            }
        }
    }

    public ArrayList<int[]> getDimacsFD() {
        return dimacsFD;
    }

    public BiMap<String, Integer> getFeatureMapping() {
        return featureMapping;
    }

    public FExpression getFd() {
        return fd;
    }

    public List<String> getFeatures() {
        return Lists.newArrayList(featureMapping.keySet());
    }

    public int getFeaturesCount() {
        return featureMapping.size();
    }

}

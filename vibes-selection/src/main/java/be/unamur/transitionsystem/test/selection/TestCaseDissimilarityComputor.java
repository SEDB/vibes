package be.unamur.transitionsystem.test.selection;

import be.unamur.transitionsystem.test.TestCase;
import be.unamur.transitionsystem.test.selection.exception.DissimilarityComputationException;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.function.Function;

public class TestCaseDissimilarityComputor implements DissimilarityComputor<TestCase> {

    private DissimilarityComputor computor;
    private Function<TestCase, ? extends Collection> converter;
    
    protected TestCaseDissimilarityComputor(){}
    
    private TestCaseDissimilarityComputor(SetBasedDissimilarityComputor computor, Function<TestCase, ? extends Collection> converter) {
        this.computor = computor;
        this.converter = converter;
    }
        
    private TestCaseDissimilarityComputor(SequenceBasedDissimilarityComputor computor, Function<TestCase, ? extends Collection> converter) {
        this.computor = computor;
        this.converter = converter;
    }

    @Override
    public double dissimilarity(TestCase tc1, TestCase tc2) throws DissimilarityComputationException {
        return this.computor.dissimilarity(converter.apply(tc1), converter.apply(tc2));
    }
    
    /**
     * Returns a new dissimilarity computor that will compute a set based dissimilarity between the actions of two test cases.
     * @param computor The dissimilarity computor to use with the sets of actions of two test cases.
     * @return A TestCaseDissimilarityComputor that will use the given dissimilarity computor to compute dissimilarity between two test cases.
     */
    public static TestCaseDissimilarityComputor toTestCaseDissimilarityComputor(SetBasedDissimilarityComputor computor){
        return new TestCaseDissimilarityComputor(computor, (TestCase testcase) -> {return Sets.newHashSet(testcase.iterator());});
    }
    
    /**
     * Returns a new dissimilarity computor that will compute a sequence based dissimilarity between the sequence of actions of two test cases.
     * @param computor The dissimilarity computor to use with the sequences of actions of two test cases.
     * @return A TestCaseDissimilarityComputor that will use the given dissimilarity computor to compute dissimilarity between two test cases.
     */
    public static TestCaseDissimilarityComputor toTestCaseDissimilarityComputor(SequenceBasedDissimilarityComputor computor){
        return new TestCaseDissimilarityComputor(computor, (TestCase testcase) -> {return Lists.newArrayList(testcase.iterator());});
    }

}

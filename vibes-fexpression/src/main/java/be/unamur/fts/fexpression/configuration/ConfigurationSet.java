package be.unamur.fts.fexpression.configuration;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.*;
import be.unamur.fts.fexpression.FExpression;
import be.unamur.fts.solver.ConstraintIdentifier;
import be.unamur.fts.solver.SolverFacade;
import be.unamur.fts.solver.SolverFatalErrorException;
import be.unamur.fts.solver.exception.ConstraintNotFoundException;
import be.unamur.fts.solver.exception.ConstraintSolvingException;
import be.unamur.fts.solver.exception.SolverInitializationException;

public class ConfigurationSet implements Set<Configuration> {

    private static final Logger logger = LoggerFactory.getLogger(ConfigurationSet.class);

    private SolverFacade solver;
    private FExpression constraint;

    public ConfigurationSet(SolverFacade solver) {
        this(solver, FExpression.trueValue());
    }

    public ConfigurationSet(SolverFacade solver, FExpression constraint) {
        this.solver = solver;
        this.constraint = constraint;
    }

    public FExpression getConstraint() {
        return constraint;
    }

    public void setConstraint(FExpression constraint) {
        checkNotNull(constraint);
        this.constraint = constraint;
    }

    @Override
    public int size() {
        try {
            ConstraintIdentifier id = solver.addConstraint(constraint);
            double nbSolutions = solver.getNumberOfSolutions();
            solver.removeConstraint(id);
            return (int) nbSolutions;
        } catch (ConstraintNotFoundException e) {
            logger.error("Error while removing constraint!", e);
        } catch (SolverFatalErrorException e) {
            logger.error("Error solving contraint!", e);
        } catch (SolverInitializationException e) {
            logger.error("Error Initializing solver!", e);
        } catch (ConstraintSolvingException e) {
            logger.error("Error solving contraint!", e);
        } catch (ClassCastException e) {
            logger.debug("Unnable to cast size value!", e);
        }
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Iterator<Configuration> iterator() {
        try {
            ConstraintIdentifier id = solver.addConstraint(constraint);
            return solver.getSolutions();
        } catch (SolverFatalErrorException e) {
            logger.error("Error solving contraint!", e);
        } catch (SolverInitializationException e) {
            logger.error("Error Initializing solver!", e);
        } catch (ConstraintSolvingException e) {
            logger.error("Error solving contraint!", e);
        } catch (ClassCastException e) {
            logger.debug("Unnable to cast size value!", e);
        }
        throw new IllegalStateException("Could not build configuration iterator!");
    }

    @Override
    public Object[] toArray() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean add(Configuration e) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean remove(Object o) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends Configuration> c) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub

    }

}

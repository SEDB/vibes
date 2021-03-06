package be.unamur.transitionsystem.test.selection;

/*
 * #%L
 * vibes-selection
 * %%
 * Copyright (C) 2014 PReCISE, University of Namur
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */
import be.unamur.transitionsystem.TransitionSystem;
import be.unamur.transitionsystem.test.TestCaseFactory;
import be.unamur.transitionsystem.test.selection.exception.TestCaseSelectionException;

public abstract class AbstractTestCaseGenerator implements TestCaseGenerator {

    private TestCaseValidator validator;
    private TestCaseFactory testCaseFactory;
    private TestCaseWrapUp wrapUp;

    public AbstractTestCaseGenerator(TestCaseFactory testCaseFactory,
            TestCaseValidator validator, TestCaseWrapUp wrapUp) {
        this.testCaseFactory = testCaseFactory;
        this.validator = validator;
        this.wrapUp = wrapUp;
    }

    protected TestCaseFactory getTestCaseFactory() {
        return this.testCaseFactory;
    }

    public void setTestCaseFactory(TestCaseFactory testCaseFactory) {
        this.testCaseFactory = testCaseFactory;
    }

    public TestCaseValidator getValidator() {
        return validator;
    }

    public void setValidator(TestCaseValidator validator) {
        this.validator = validator;
    }

    public TestCaseWrapUp getWrapUp() {
        return wrapUp;
    }

    public void setWrapUp(TestCaseWrapUp wrapUp) {
        this.wrapUp = wrapUp;
    }

    @Override
    public void generateAbstractTestSet(TransitionSystem ts)
            throws TestCaseSelectionException {
        generateAbstractTestSet(ts, validator, wrapUp);
    }

    @Override
    public void generateAbstractTestSet(TransitionSystem ts,
            TestCaseWrapUp wrapUp) throws TestCaseSelectionException {
        generateAbstractTestSet(ts, validator, wrapUp);

    }

    @Override
    public void generateAbstractTestSet(TransitionSystem ts,
            TestCaseValidator validator) throws TestCaseSelectionException {
        generateAbstractTestSet(ts, validator, wrapUp);
    }

    @Override
    public abstract void generateAbstractTestSet(TransitionSystem ts,
            TestCaseValidator validator, TestCaseWrapUp wrapUp)
            throws TestCaseSelectionException;
}

package be.unamur.transitionsystem.test;

/*
 * #%L vibes-core %% Copyright (C) 2014 PReCISE, University of Namur %%
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
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE. #L%
 */
import com.google.common.collect.HashMultiset;

import be.unamur.transitionsystem.Action;
import be.unamur.transitionsystem.State;
import be.unamur.transitionsystem.Transition;
import be.unamur.transitionsystem.test.exception.TestCaseException;
import com.google.common.collect.Lists;

/**
 * A mutable implementation of test cases for LTSs.
 *
 * @author Xavier Devroey - xavier.devroey@unamur.be
 *
 */
public class LtsMutableTestCase extends MutableTestCase<Action, State, Transition> {

    /**
     * The {@link TestCaseFactory} implementation for {@link LtsMutableTestCase}
     * test cases.
     */
    public static final TestCaseFactory FACTORY = new TestCaseFactory() {
        @Override
        public TestCase buildTestCase() {
            return new LtsMutableTestCase();
        }
    };

    public LtsMutableTestCase() {
        super();
    }

    @Override
    public LtsMutableTestCase copy() throws TestCaseException {
        LtsMutableTestCase tc = new LtsMutableTestCase();
        tc.actions = Lists.newArrayList(actions);
        tc.actionSet = HashMultiset.create(actionSet);
        return tc;
    }

}

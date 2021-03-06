package be.unamur.transitionsystem.fts;

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
import be.unamur.transitionsystem.TransitionSystemElementFactory;
import be.unamur.transitionsystem.Action;
import be.unamur.transitionsystem.State;

/**
 * Factory class for FTS elements.
 *
 * @author Xavier Devroey - xavier.devroey@unamur.be
 *
 */
public class FeaturedTransitionSystemElementFactory implements
        TransitionSystemElementFactory<State, FeaturedTransition, Action> {

    public FeaturedTransitionSystemElementFactory() {
    }

    @Override
    public FeaturedTransition buildTransition() {
        return new FeaturedTransition();
    }

    @Override
    public FeaturedTransition buildTransition(State from, State to, Action action) {
        return new FeaturedTransition(from, to, action);
    }

    @Override
    public State buildState() {
        return new State();
    }

    @Override
    public State buildState(String name) {
        return new State(name);
    }

    @Override
    public Action buildAction() {
        return new Action();
    }

    @Override
    public Action buildAction(String name) {
        return new Action(name);
    }

}

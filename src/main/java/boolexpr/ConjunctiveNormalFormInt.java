package boolexpr;

/*-
 * #%L
 * BoolExpr
 * %%
 * Copyright (C) 2020 Timothy Hoffman
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import boolexpr.util.SparseBitSet;

/**
 * Conjunctive Normal Form is a boolean formula which is an AND of ORs.
 *
 * @author Timothy Hoffman
 */
public class ConjunctiveNormalFormInt extends NormalFormInt<ConjunctiveNormalFormInt> {

    private static final FormRules RULES = FormRules.CONJUNCTIVE;

    /**
     * Creates a {@link ConjunctiveNormalFormInt} with a single phrase.
     *
     * NOTE: If the given {@link SparseBitSet} is empty, this is equivalent to
     * {@link #getFalse()}. If {@code null}, then it's equivalent to
     * {@link #getTrue()}.
     *
     * @param firstPhrase
     */
    protected ConjunctiveNormalFormInt(SparseBitSet firstPhrase) {
        super(RULES, firstPhrase);
    }

    /**
     * Create a {@link ConjunctiveNormalFormInt} with a single proposition.
     *
     * NOTE: If the given {@link Integer} is {@code null}, this is equivalent to
     * {@link #getFalse()}.
     *
     * @param firstProp
     */
    public ConjunctiveNormalFormInt(Integer firstProp) {
        super(RULES, firstProp);
    }

    /**
     * Creates an empty {@link ConjunctiveNormalFormInt}. This is equivalent to
     * {@link #getTrue()}.
     *
     */
    public ConjunctiveNormalFormInt() {
        super(RULES);
    }

    /**
     * Create a new {@link ConjunctiveNormalFormInt} by performing a deep copy
     * of an existing {@link ConjunctiveNormalFormInt}.
     *
     * @param original     the {@link ConjunctiveNormalFormInt} to duplicate
     * @param unmodifiable whether or not the new instance should be marked as
     *                     unmodifiable/immutable
     */
    public ConjunctiveNormalFormInt(ConjunctiveNormalFormInt original, boolean unmodifiable) {
        super(original, unmodifiable);
    }

    /**
     * Copy constructor, performs a deep copy of the given
     * {@link ConjunctiveNormalFormInt}.
     *
     * NOTE: the resulting {@link ConjunctiveNormalFormInt} is modifiable.
     *
     * @param original the {@link ConjunctiveNormalFormInt} to duplicate
     */
    protected ConjunctiveNormalFormInt(ConjunctiveNormalFormInt original) {
        this(original, false);
    }

    @Override
    public ConjunctiveNormalFormInt clone(boolean unmodifiable) {
        return new ConjunctiveNormalFormInt(this, unmodifiable);
    }

    @Override
    protected ConjunctiveNormalFormInt create(SparseBitSet firstPhrase) {
        return new ConjunctiveNormalFormInt(firstPhrase);
    }

    public static ConjunctiveNormalFormInt getFalse() {
        return RULES.getFalse(new ConjunctiveNormalFormInt());
    }

    public static ConjunctiveNormalFormInt getTrue() {
        return RULES.getTrue(new ConjunctiveNormalFormInt());
    }

    /**
     * Returns a new {@link ConjunctiveNormalFormInt} that is the result of
     * performing the "and" operation on the two inputs.
     *
     * @param in1
     * @param in2
     *
     * @return
     */
    public static ConjunctiveNormalFormInt and(ConjunctiveNormalFormInt in1, ConjunctiveNormalFormInt in2) {
        return new ConjunctiveNormalFormInt(in1).and(in2);
    }

    /**
     * Returns a new {@link ConjunctiveNormalFormInt} that is the result of
     * performing the "and" operation on the two inputs.
     *
     * @param in1
     * @param in2
     *
     * @return
     */
    public static ConjunctiveNormalFormInt and(Integer in1, Integer in2) {
        return new ConjunctiveNormalFormInt(in1).and(in2);
    }

    /**
     * Returns a new {@link ConjunctiveNormalFormInt} that is the result of
     * performing the "or" operation on the two inputs.
     *
     * @param in1
     * @param in2
     *
     * @return
     */
    public static ConjunctiveNormalFormInt or(ConjunctiveNormalFormInt in1, ConjunctiveNormalFormInt in2) {
        return new ConjunctiveNormalFormInt(in1).or(in2);
    }

    /**
     * Returns a new {@link ConjunctiveNormalFormInt} that is the result of
     * performing the "or" operation on the two inputs.
     *
     * @param in1
     * @param in2
     *
     * @return
     */
    public static ConjunctiveNormalFormInt or(Integer in1, Integer in2) {
        return new ConjunctiveNormalFormInt(in1).or(in2);
    }

    @Override
    public String toString() {
        return toString(false);
    }

    /**
     * Builds a formatted {@link String} representation of {@code this} with the
     * option to sort the items in the {@link String} returned.
     *
     * @param sorted
     *
     * @return
     */
    public String toString(boolean sorted) {
        return "<" + super.toString(PrintingConnectives.CONJUNCTIVE_STD, sorted) + ">";
    }

    /**
     * Parses a {@link ConjunctiveNormalFormInt} from the given string (in the
     * format generated by {@link #toString(boolean)}.
     *
     * @param inputStr
     *
     * @return
     */
    public static ConjunctiveNormalFormInt fromString(String inputStr) {
        //Peel off < and > from the beginning and end (resp.)
        if (inputStr.startsWith("<")) {
            inputStr = inputStr.substring(1);
        }
        if (inputStr.endsWith(">")) {
            inputStr = inputStr.substring(0, inputStr.length() - 1);
        }
        //Use the superclass implementation
        ConjunctiveNormalFormInt retVal = new ConjunctiveNormalFormInt();
        retVal.fromString(inputStr, PrintingConnectives.CONJUNCTIVE_STD);
        return retVal;
    }

    /**
     * Builds a csv formatted {@link String} representation of {@code this} with
     * the option to sort the items in the {@link String} returned.
     *
     * @param sorted
     *
     * @return
     */
    public String toCSVstring(boolean sorted) {
        return super.toString(PrintingConnectives.CONJUNCTIVE_CSV, sorted);
    }

    /**
     * Parses a {@link ConjunctiveNormalFormInt} from the given string (in the
     * format generated by {@link #toCSVstring(boolean)}.
     *
     * @param inputStr
     *
     * @return
     */
    public static ConjunctiveNormalFormInt fromCSVString(String inputStr) {
        //Use the superclass implementation
        ConjunctiveNormalFormInt retVal = new ConjunctiveNormalFormInt();
        retVal.fromString(inputStr, PrintingConnectives.CONJUNCTIVE_CSV);
        return retVal;
    }
}

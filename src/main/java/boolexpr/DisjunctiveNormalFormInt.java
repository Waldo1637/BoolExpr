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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Disjunctive Normal Form is a boolean formula which is an OR of ANDs.
 *
 * @author Timothy Hoffman
 */
public class DisjunctiveNormalFormInt extends NormalFormInt<DisjunctiveNormalFormInt> {

    private static final FormRules RULES = FormRules.DISJUNCTIVE;

    /**
     * Creates a {@link DisjunctiveNormalFormInt} with a single phrase.
     *
     * NOTE: If the given {@link SparseBitSet} is empty, this is equivalent to
     * {@link #getTrue()}. If {@code null}, then it's equivalent to
     * {@link #getFalse()}.
     *
     * @param firstPhrase
     */
    public DisjunctiveNormalFormInt(SparseBitSet firstPhrase) {
        super(RULES, firstPhrase);
    }

    /**
     * Create a {@link DisjunctiveNormalFormInt} with a single proposition.
     *
     * NOTE: If the given {@link Integer} is {@code null}, this is equivalent to
     * {@link #getTrue()}.
     *
     * @param firstProp
     */
    public DisjunctiveNormalFormInt(Integer firstProp) {
        super(RULES, firstProp);
    }

    /**
     * Creates an empty {@link DisjunctiveNormalFormInt}. This is equivalent to
     * {@link #getFalse()}.
     */
    public DisjunctiveNormalFormInt() {
        super(RULES);
    }

    /**
     * Create a new {@link DisjunctiveNormalFormInt} by performing a deep copy
     * of an existing {@link DisjunctiveNormalFormInt}.
     *
     * @param original     the {@link DisjunctiveNormalFormInt} to duplicate
     * @param unmodifiable whether or not the new instance should be marked as
     *                     unmodifiable/immutable
     */
    public DisjunctiveNormalFormInt(DisjunctiveNormalFormInt original, boolean unmodifiable) {
        super(original, unmodifiable);
    }

    /**
     * Copy constructor, performs a deep copy of the given
     * {@link DisjunctiveNormalFormInt}.
     *
     * NOTE: the resulting {@link DisjunctiveNormalFormInt} is modifiable.
     *
     * @param original the {@link DisjunctiveNormalFormInt} to duplicate
     */
    public DisjunctiveNormalFormInt(DisjunctiveNormalFormInt original) {
        super(original, false);
    }

    @Override
    public DisjunctiveNormalFormInt clone(boolean unmodifiable) {
        return new DisjunctiveNormalFormInt(this, unmodifiable);
    }

    @Override
    protected DisjunctiveNormalFormInt create(SparseBitSet firstPhrase) {
        return new DisjunctiveNormalFormInt(firstPhrase);
    }

    public static DisjunctiveNormalFormInt getFalse() {
        return RULES.getFalse(new DisjunctiveNormalFormInt());
    }

    public static DisjunctiveNormalFormInt getTrue() {
        return RULES.getTrue(new DisjunctiveNormalFormInt());
    }

    /**
     * Returns a new {@link DisjunctiveNormalFormInt} that is the result of
     * performing the "and" operation on the two inputs.
     *
     * @param in1
     * @param in2
     *
     * @return
     */
    public static DisjunctiveNormalFormInt and(DisjunctiveNormalFormInt in1, DisjunctiveNormalFormInt in2) {
        return new DisjunctiveNormalFormInt(in1).and(in2);
    }

    /**
     * Returns a new {@link DisjunctiveNormalFormInt} that is the result of
     * performing the "and" operation on the two inputs.
     *
     * @param in1
     * @param in2
     *
     * @return
     */
    public static DisjunctiveNormalFormInt and(Integer in1, Integer in2) {
        return new DisjunctiveNormalFormInt(in1).and(in2);
    }

    /**
     * Returns a new {@link DisjunctiveNormalFormInt} that is the result of
     * performing the "or" operation on the two inputs.
     *
     * @param in1
     * @param in2
     *
     * @return
     */
    public static DisjunctiveNormalFormInt or(DisjunctiveNormalFormInt in1, DisjunctiveNormalFormInt in2) {
        return new DisjunctiveNormalFormInt(in1).or(in2);
    }

    /**
     * Returns a new {@link DisjunctiveNormalFormInt} that is the result of
     * performing the "or" operation on the two inputs.
     *
     * @param in1
     * @param in2
     *
     * @return
     */
    public static DisjunctiveNormalFormInt or(Integer in1, Integer in2) {
        return new DisjunctiveNormalFormInt(in1).or(in2);
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
        return "<" + super.toString(PrintingConnectives.DISJUNCTIVE_STD, sorted) + ">";
    }

    /**
     * Parses a {@link DisjunctiveNormalFormInt} from the given string (in the
     * format generated by {@link #toString(boolean)}.
     *
     * @param inputStr
     *
     * @return
     */
    public static DisjunctiveNormalFormInt fromString(String inputStr) {
        //Peel off < and > from the beginning and end (resp.)
        if (inputStr.startsWith("<")) {
            inputStr = inputStr.substring(1);
        }
        if (inputStr.endsWith(">")) {
            inputStr = inputStr.substring(0, inputStr.length() - 1);
        }
        //Use the superclass implementation
        DisjunctiveNormalFormInt retVal = new DisjunctiveNormalFormInt();
        retVal.fromString(inputStr, PrintingConnectives.DISJUNCTIVE_STD);
        return retVal;
    }

    /**
     * Builds a CSV formatted {@link String} representation of {@code this} with
     * the option to sort the items in the {@link String} returned.
     *
     * @param sorted
     *
     * @return
     */
    public String toCSVstring(boolean sorted) {
        return super.toString(PrintingConnectives.DISJUNCTIVE_CSV, sorted);
    }

    /**
     * Parses a {@link DisjunctiveNormalFormInt} from the given string (in the
     * format generated by {@link #toCSVstring(boolean)}.
     *
     * @param inputStr
     *
     * @return
     */
    public static DisjunctiveNormalFormInt fromCSVString(String inputStr) {
        //Use the superclass implementation
        DisjunctiveNormalFormInt retVal = new DisjunctiveNormalFormInt();
        retVal.fromString(inputStr, PrintingConnectives.DISJUNCTIVE_CSV);
        return retVal;
    }

    public void simplifyWithMutexNodes(SparseBitSet mutexNodes, SparseBitSet joinPoints, boolean debug) {
        checkModifiability();
        //TODO: this is hardcoded for k=2 so skip anything else for now
        if (mutexNodes.cardinality() == 2) {
            //First step is to group the phrases by cardinality. This will reduce
            //  the number of unnecessary phrase matchups in the later n^2 loop.
            HashMap<Integer, ArrayList<SparseBitSet>> groupedByCardinality = new HashMap<>();
            for (SparseBitSet phrase : this.data) {
                //NOTE: the usage of 'phrase' and 'mutexNodes' in the call to
                //  SparseBitSet#intersects(SparseBitSet) could be swapped but
                //  using 'mutexNodes' as the base is almost always faster b/c
                //  it's gauranteed to be size 2 where phrase could be larger.
                if (mutexNodes.intersects(phrase)) {//only keep if 'phrase' contains something from 'mutexNodes'
                    int card = phrase.cardinality();
                    ArrayList<SparseBitSet> group = groupedByCardinality.get(card);
                    if (group == null) {
                        groupedByCardinality.put(card, group = new ArrayList<>());
                    }
                    group.add(phrase);
                }
            }
            if (debug) {
                StringBuilder sb = new StringBuilder();
                sb.append("{");
                for (Map.Entry<Integer, ArrayList<SparseBitSet>> e : groupedByCardinality.entrySet()) {
                    sb.append("#").append(e.getKey()).append("=").append(e.getValue().size()).append(",");
                }
                sb.append("}");
                System.out.println("Cardinality groups (" + groupedByCardinality.size() + "): " + sb);
            }
            //Now, process each group separately
            for (Map.Entry<Integer, ArrayList<SparseBitSet>> e : groupedByCardinality.entrySet()) {
                final ArrayList<SparseBitSet> phrases = e.getValue();
                //NOTE: although it might seem like a good optimization to skip
                //  any group containing fewer than 2 phrases (which naturally
                //  includes the group with cardinality 0 that should obviously
                //  be skipped), there's no need to have a separate check for it
                //  b/c such a group will never make it into the second loop.
                //
                //Check all pairs of phrases with the same cardinality
                //
                //TODO: this nested loop structure could be done in parallel,
                //  just need to make sure tryAddWithAbsorption(..) and
                //  data.remove(..) are synchronized or queued.
                //
                for (int i = 0; i < phrases.size(); i++) {
                    SparseBitSet phraseI = phrases.get(i);
                    //ASSERT: no phrase should contain more than one of 'mutexNodes'
                    assertNoForbiddenSiblings(phraseI, mutexNodes);
                    for (int j = i + 1; j < phrases.size(); j++) {
                        SparseBitSet phraseJ = phrases.get(j);

                        if (debug) {
                            System.out.println("    Checking " + phraseI + " and " + phraseJ);
                        }

                        //compute XOR of the phrases and see if it matches the mutual excluder
                        SparseBitSet xor = SparseBitSet.xor(phraseI, phraseJ);
                        if (mutexNodes.equals(xor)) {
                            if (debug) {
                                System.out.println("      mutual excluders (" + joinPoints + "," + mutexNodes + ") found in " + phraseI + " and " + phraseJ);
                                System.out.println("      before: " + toString());
                            }

                            //Remove the phrases that are alike (other than the
                            //  mutual excluders) from 'this'
                            this.data.remove(phraseI);
                            this.data.remove(phraseJ);

                            //The intersection between the two phrases will be preserved
                            //  even after siblings are found and removed
                            //
                            //TODO: since a phrase cannot be matched again, if we
                            //  have already removed it from 'data' we could consume
                            //  it here rather than cloning (done inside and(x,y)).
                            //
                            //
                            SparseBitSet intersection = SparseBitSet.and(phraseI, phraseJ);

                            //For each join point in the current MutualExcluder, add
                            //  a replacement phrase containing the intersection and
                            //  the join point
                            for (int join = joinPoints.minSetBit(); join >= 0; join = joinPoints.nextSetBit(join + 1)) {
                                //Copy intersection and add joinpoint
                                SparseBitSet newSet = intersection.clone();
                                newSet.set(join);
                                this.tryAddWithAbsorption(newSet);
                            }

                            if (debug) {
                                System.out.println("      after: " + toString());
                            }

                            //NOTE: Since we are considering a single pair of mutually
                            //  exclusive nodes, <A,B>, once two phrases has been found
                            //  that are identical except one contains A and the other
                            //  contains B, it is not possible for either phrase to
                            //  match with any other phrase (this follows from the
                            //  facts that there are no identical phrases and that 
                            //  a phrase cannot contain both A and B). Thus, we can
                            //  remove the phrases from the list.
                            //To prevent phraseJ from comparing against any other
                            //  phrase, we remove it from the list.
                            phrases.remove(phraseJ);
                            //To prevent phraseI from comparing against any other
                            //  phrase, we just break the inner loop.
                            break;
                        }//end if (xor.equals)
                    }//end for j
                }//end for i    
            }//end for phrase groupings
        }//end if(mutexNodes.cardinality)
    }

    /**
     * Remove from {@code this}, all phrases that contain more than one of the
     * elements in the specified {@link SparseBitSet}.
     *
     * @param forbiddenSiblings
     */
    public void removeForbiddenPhrases(SparseBitSet forbiddenSiblings) {
        checkModifiability();
        for (Iterator<SparseBitSet> it = data.iterator(); it.hasNext();) {
            SparseBitSet phrase = it.next();
            if (intersectsMoreThanOnce(phrase, forbiddenSiblings)) {
                it.remove();
            }
        }
    }

    /**
     * @param phrase
     * @param forbiddenSiblings
     *
     * @return {@code true} iff the {@code phrase} {@link SparseBitSet} contains
     *         more than one of the elements in the {@code forbiddenSiblings}
     *         {@link SparseBitSet}
     */
    public static boolean containsForbiddenSiblings(SparseBitSet phrase, SparseBitSet forbiddenSiblings) {
        return intersectsMoreThanOnce(phrase, forbiddenSiblings);
    }

    /**
     * Search {@code this} for any phrase that contains more than one of the
     * elements in the {@code forbiddenSiblings} {@link SparseBitSet}. If any
     * such phrase is found, returns {@code true}. Otherwise, {@code false}.
     *
     * @param forbiddenSiblings
     *
     * @return
     */
    public boolean containsForbiddenSiblings(SparseBitSet forbiddenSiblings) {
        for (SparseBitSet phrase : data) {
            if (intersectsMoreThanOnce(phrase, forbiddenSiblings)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Asserts that the given {@code phrase} {@link SparseBitSet} does not
     * contain more than one of the elements in the {@code forbiddenSiblings}
     * {@link SparseBitSet}
     *
     * @param phrase
     * @param forbiddenSiblings
     */
    public static void assertNoForbiddenSiblings(SparseBitSet phrase, SparseBitSet forbiddenSiblings) {
        assert !intersectsMoreThanOnce(phrase, forbiddenSiblings) :
                "Phrase " + phrase + " contains more than one mutex element from " + forbiddenSiblings;
    }

    /**
     * For every phrase in {@code this}, asserts that the phrase does not
     * contain more than one of the elements in the {@code forbiddenSiblings}
     * {@link SparseBitSet}
     *
     * @param forbiddenSiblings
     */
    public void assertNoForbiddenSiblings(SparseBitSet forbiddenSiblings) {
        for (SparseBitSet phrase : data) {
            assertNoForbiddenSiblings(phrase, forbiddenSiblings);
        }
    }

    private static boolean intersectsMoreThanOnce(SparseBitSet a, SparseBitSet b) {
        boolean intersectionFound = false;
        for (int i = a.minSetBit(), j = b.nextSetBit(i); i >= 0 && j >= 0;) {
            if (i < j) {
                //No match found, advance only 'i'
                i = a.nextSetBit(i + 1);
            } else if (i > j) {
                //No match found, advance only 'j'
                j = b.nextSetBit(j + 1);
            } else {
                //Match found!
                if (intersectionFound) {
                    //There was also an earlier match found so return true
                    return true;
                }
                //Otherwise, mark that the first match was found and advance both
                intersectionFound = true;
                i = a.nextSetBit(i + 1);
                j = b.nextSetBit(j + 1);
            }
        }
        //If the end of either was reached, then there were fewer than 2 matches
        return false;
    }
}

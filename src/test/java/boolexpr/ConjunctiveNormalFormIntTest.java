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
import org.junit.*;

/**
 *
 * @author Timothy Hoffman
 */
public class ConjunctiveNormalFormIntTest extends NormalFormTestBase<SparseBitSet, Integer, ConjunctiveNormalFormInt> {

    public ConjunctiveNormalFormIntTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    //called before each test method
    @Before
    public void setUp() {
    }

    //called after each test method
    @After
    public void tearDown() {
    }

    @Override
    protected Construction<SparseBitSet, Integer, ConjunctiveNormalFormInt> getCons() {
        return Construction.CNF_INT;
    }

    @Test
    public void testFromString() {
        System.out.println("test_fromString");
        Construction<SparseBitSet, Integer, ConjunctiveNormalFormInt> cons = getCons();

        String strT = cons.staticGetTrue().toString(TestHelpers.FORMAT, true);
        ConjunctiveNormalFormInt dnfT = ConjunctiveNormalFormInt.fromString(strT);
        Assert.assertEquals(strT, dnfT.toString(TestHelpers.FORMAT, true));
        Assert.assertFalse(dnfT.isFalse());
        Assert.assertTrue(dnfT.isTrue());

        String strF = cons.staticGetFalse().toString(TestHelpers.FORMAT, true);
        ConjunctiveNormalFormInt dnfF = ConjunctiveNormalFormInt.fromString(strF);
        Assert.assertEquals(strF, dnfF.toString(TestHelpers.FORMAT, true));
        Assert.assertTrue(dnfF.isFalse());
        Assert.assertFalse(dnfF.isTrue());

        String str1 = "<(19766|57990)&(19938|57990)&(53437|57990)&(53500|57990)&(56892|57990)&(56914|57990)&(57990|58949)&(57990|59043)&(57990|59118)&(57990|60847)&(57990|120810)>";
        ConjunctiveNormalFormInt dnf1 = ConjunctiveNormalFormInt.fromString(str1);
        Assert.assertEquals(str1, dnf1.toString(true));

        String str2 = "<(2|5)&(4|7|7)&(5)>";//absorption is applied
        ConjunctiveNormalFormInt dnf2 = ConjunctiveNormalFormInt.fromString(str2);
        Assert.assertEquals("<(5)&(4|7)>", dnf2.toString(true));

        String str3 = "<(2|5)&(4|7)&(5)&()>";//absorption is applied
        ConjunctiveNormalFormInt dnf3 = ConjunctiveNormalFormInt.fromString(str3);
        Assert.assertEquals("<()>", dnf3.toString(true));

        String str4 = "<(2|5)&(4|7)&(5|7)&>";//due to split, extra separator at the end is okay/ignored
        ConjunctiveNormalFormInt dnf4 = ConjunctiveNormalFormInt.fromString(str4);
        Assert.assertEquals("<(2|5)&(4|7)&(5|7)>", dnf4.toString(true));

        String str5 = "<(2|5)&&(4|7)>";//extra separator in the middle is also okay/ignored if phrase wraps are not empty
        ConjunctiveNormalFormInt dnf5 = ConjunctiveNormalFormInt.fromString(str5);
        Assert.assertEquals("<(2|5)&(4|7)>", dnf5.toString(true));

        String str6 = "<(2|5)&(4|7)&(5|)>";//due to split, extra separator at the end is okay/ignored
        ConjunctiveNormalFormInt dnf6 = ConjunctiveNormalFormInt.fromString(str6);
        Assert.assertEquals("<(5)&(4|7)>", dnf6.toString(true));

        String str7 = "<(|2||5)&(4||||||7)&(5|)>";//extra separator in the middle is also okay/ignored iff parseElement returns null for empty string
        ConjunctiveNormalFormInt dnf7 = ConjunctiveNormalFormInt.fromString(str7);
        Assert.assertEquals("<(5)&(4|7)>", dnf7.toString(true));

        String str8a = "(2|5)&(4|7)&(5)";//missing outer angle brackets is allowed although not recommended
        ConjunctiveNormalFormInt dnf8a = ConjunctiveNormalFormInt.fromString(str8a);
        Assert.assertEquals("<(5)&(4|7)>", dnf8a.toString(true));

        String str8b = "<(2|5)&(4|7)&(5)";//mis-matched outer angle brackets is allowed although not recommended
        ConjunctiveNormalFormInt dnf8b = ConjunctiveNormalFormInt.fromString(str8b);
        Assert.assertEquals("<(5)&(4|7)>", dnf8b.toString(true));

        String str8c = "(2|5)&(4|7)&(5)>";//mis-matched outer angle brackets is allowed although not recommended
        ConjunctiveNormalFormInt dnf8c = ConjunctiveNormalFormInt.fromString(str8c);
        Assert.assertEquals("<(5)&(4|7)>", dnf8c.toString(true));

        //TODO: test some more reasonable combinations
    }

    @Test
    public void testFromStringInvalid1() {
        System.out.println("test_fromString_invalid_1");

        String str = "<(2|5)&(4|7)(5)>";//missing connectives are not allowed
        thrown.expect(java.lang.IllegalArgumentException.class);
        ConjunctiveNormalFormInt.fromString(str);
    }

    @Test
    public void testFromStringInvalid2() {
        System.out.println("test_fromString_invalid_2");

        String str = "<(2|5)+(4|7)+(5)>";//incorrect connectives are not allowed
        thrown.expect(java.lang.IllegalArgumentException.class);
        ConjunctiveNormalFormInt.fromString(str);
    }

    @Test
    public void testFromStringInvalid3() {
        System.out.println("test_fromString_invalid_3");

        String str = "<2|5 4|7 5>";//incorrect connectives are not allowed
        thrown.expect(java.lang.IllegalArgumentException.class);
        ConjunctiveNormalFormInt.fromString(str);
    }

    @Test
    public void testFromStringInvalid4() {
        System.out.println("test_fromString_invalid_4");

        String str = "<)2|5(&(4|7)&(5)>";//incorrect phrase wrappers are not allowed
        thrown.expect(java.lang.IllegalArgumentException.class);
        ConjunctiveNormalFormInt.fromString(str);
    }

    @Test
    public void testFromStringInvalid5() {
        System.out.println("test_fromString_invalid_5");

        String str = "<2|5&4|7&5>";//missing phrase wrappers are not allowed
        thrown.expect(java.lang.IllegalArgumentException.class);
        ConjunctiveNormalFormInt.fromString(str);
    }
}

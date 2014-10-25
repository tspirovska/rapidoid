package org.rapidoid.util;

/*
 * #%L
 * rapidoid-utils
 * %%
 * Copyright (C) 2014 Nikolche Mihajlovski
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.rapidoid.test.TestCommons;
import org.testng.annotations.Test;

public class UTest extends TestCommons {

	@Test(enabled = false)
	public void testPropertiesOf() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testKindOfClassOfQ() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testKindOfObject() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testTraceString() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testTraceStringStringObject() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testTraceStringStringObjectStringObject() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testTraceStringStringObjectStringObjectStringObject() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testDebugString() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testDebugStringStringObject() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testDebugStringStringObjectStringObject() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testDebugStringStringObjectStringObjectStringObject() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testInfoString() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testInfoStringStringObject() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testInfoStringStringObjectStringObject() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testInfoStringStringObjectStringObjectStringObject() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testWarnString() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testWarnStringStringObject() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testWarnStringStringObjectStringObject() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testWarnStringStringObjectStringObjectStringObject() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testWarnStringThrowable() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testErrorString() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testErrorStringStringObject() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testErrorStringStringObjectStringObject() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testErrorStringStringObjectStringObjectStringObject() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testErrorStringThrowable() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testSevereString() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testSevereStringStringObject() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testSevereStringStringObjectStringObject() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testSevereStringStringObjectStringObjectStringObject() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testSevereStringThrowable() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testSleep() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testWaitInterruption() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testWaitForObject() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testSetFieldValueObjectStringObject() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testSetFieldValueFieldObjectObject() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testGetFieldValueObjectString() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testGetFieldValueFieldObject() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testGetFields() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testGetFieldsAnnotated() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testGetMethod() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testFindMethod() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testInvokeStatic() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testInvoke() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testGetImplementedInterfaces() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testGetConstructor() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testAnnotatedMethod() {
		fail("Not yet implemented");
	}

	@Test
	public void testTextCollectionOfObject() {
		eq(U.text(new ArrayList<Integer>()), "[]");

		List<String> lst = new ArrayList<String>();

		lst.add("java");
		lst.add("c");
		lst.add("c++");

		eq(U.text(lst), "[java, c, c++]");
	}

	@Test
	public void testTextObject() {
		eq(U.text((Object) null), "null");

		eq(U.text(123), "123");
		eq(U.text(1.23), "1.23");

		eq(U.text(true), "true");
		eq(U.text(false), "false");

		eq(U.text(""), "");
		eq(U.text("abc"), "abc");

		eq(U.text(new byte[] { -50, 0, 9 }), "[-50, 0, 9]");
		eq(U.text(new short[] { -500, 0, 9 }), "[-500, 0, 9]");
		eq(U.text(new int[] { 300000000, 70, 100 }), "[300000000, 70, 100]");
		eq(U.text(new long[] { 3000000000000000000L, 1, -8900000000000000000L }),
				"[3000000000000000000, 1, -8900000000000000000]");

		eq(U.text(new float[] { -30.40000000f, -1.587f, 89.3f }), "[-30.4, -1.587, 89.3]");
		eq(U.text(new double[] { -9987.1, -1.5, 8.3 }), "[-9987.1, -1.5, 8.3]");

		eq(U.text(new boolean[] { true }), "[true]");

		eq(U.text(new char[] { 'k', 'o', 'h' }), "[k, o, h]");
		eq(U.text(new char[] { '-', '.', '+' }), "[-, ., +]");
	}

	@Test
	public void testTextObjectArray() {
		eq(U.text(new Object[] {}), "[]");
		eq(U.text(new Object[] { 1, new boolean[] { true, false }, 3 }), "[1, [true, false], 3]");
		eq(U.text(new Object[] { new double[] { -9987.1 }, new char[] { 'a', '.' }, new int[] { 300, 70, 100 } }),
				"[[-9987.1], [a, .], [300, 70, 100]]");

		eq(U.text(new int[][] { { 1, 2 }, { 3, 4, 5 } }), "[[1, 2], [3, 4, 5]]");

		eq(U.text(new String[][][] { { { "a" }, { "r" } }, { { "m" } } }), "[[[a], [r]], [[m]]]");
	}

	@Test(enabled = false)
	public void testTextIteratorOfQ() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testTextln() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testReplaceText() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testJoinObjectArrayString() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testJoinIterableOfQString() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testRenderObjectArrayStringString() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testRenderIterableOfQStringString() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testResource() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testTime() {
		fail("Not yet implemented");
	}

	@Test
	public void testXor() {
		eq(U.xor(true, true), false);
		eq(U.xor(true, false), true);
		eq(U.xor(false, true), true);
		eq(U.xor(false, false), false);
	}

	@Test
	public void testEq() {
		isTrue(U.eq("2", "2"));
		isFalse(U.eq("2", "3"));
		isTrue(U.eq("2", "2"));
		isFalse(U.eq("a", "b"));
		isFalse(U.eq('a', 'b'));

		isFalse(U.eq(null, 'b'));
		isFalse(U.eq('a', null));
		isTrue(U.eq(null, null));
	}

	@Test(enabled = false)
	public void testFailIf() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testLoad() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testSave() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testDelete() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testExpandTArrayInt() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testExpandTArrayT() {
		fail("Not yet implemented");
	}

	@Test
	public void testSubarray() {
		String[] arr = new String[] { "aa", "bb", "c", "ddd", "e" };

		String[] subarr = U.subarray(arr, 0, 2);
		eq(subarr, new String[] { "aa", "bb", "c" });

		subarr = U.subarray(arr, 2, 4);
		eq(subarr, new String[] { "c", "ddd", "e" });

		subarr = U.subarray(arr, 0, 4);
		eq(subarr, new String[] { "aa", "bb", "c", "ddd", "e" });

		subarr = U.subarray(arr, 3, 3);
		eq(subarr, new String[] { "ddd" });

		subarr = U.subarray(arr, 1, 3);
		eq(subarr, new String[] { "bb", "c", "ddd" });
	}

	@Test(expectedExceptions = { RuntimeException.class })
	public void testSubarrayException() {
		U.subarray(new String[] { "aa", "bb", "c" }, 2, 1);
	}

	@Test
	public void testSet() {
		Set<Integer> set = U.set(1, 3, 5, 8);

		eq((set.size()), 4);

		isTrue(set.contains(1));
		isTrue(set.contains(3));
		isTrue(set.contains(5));
		isTrue(set.contains(8));
	}

	@Test
	public void testList() {
		List<String> list = U.list("m", "k", "l");

		eq((list.size()), 3);

		eq((list.get(0)), "m");
		eq((list.get(1)), "k");
		eq((list.get(2)), "l");
	}

	@Test
	public void testMap() {
		Map<String, Integer> map = U.map();

		isTrue((map.isEmpty()));
	}

	@Test
	public void testMapKV() {
		Map<String, Integer> map = U.map("a", 1);

		eq((map.size()), 1);

		eq((map.get("a").intValue()), 1);
	}

	@Test
	public void testMapKVKV() {
		Map<String, Integer> map = U.map("a", 1, "b", 2);

		eq((map.size()), 2);

		eq((map.get("a").intValue()), 1);
		eq((map.get("b").intValue()), 2);
	}

	@Test
	public void testMapKVKVKV() {
		Map<String, Integer> map = U.map("a", 1, "b", 2, "c", 3);

		eq((map.size()), 3);

		eq((map.get("a").intValue()), 1);
		eq((map.get("b").intValue()), 2);
		eq((map.get("c").intValue()), 3);
	}

	@Test
	public void testMapKVKVKVKV() {
		Map<String, Integer> map = U.map("a", 1, "b", 2, "c", 3, "d", 4);

		eq((map.size()), 4);

		eq((map.get("a").intValue()), 1);
		eq((map.get("b").intValue()), 2);
		eq((map.get("c").intValue()), 3);
		eq((map.get("d").intValue()), 4);
	}

	@Test(enabled = false)
	public void testAutoExpandingMapF1OfVK() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testAutoExpandingMapClassOfV() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testWaitForAtomicBoolean() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testWaitForAtomicIntegerInt() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testSerializeObject() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testDeserializeByteArray() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testSerializeObjectByteBuffer() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testDeserializeByteBuffer() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testEncode() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testDecode() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testExpandByteBufferInt() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testExpandByteBuffer() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testBuf2str() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testBuf() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testCopyNtimes() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testRteStringThrowable() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testRteThrowable() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testRteStringObjectArray() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testRteStringThrowableObjectArray() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testEnsureBoolean() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testEnsureBooleanString() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testEnsureBooleanStringLong() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testEnsureBooleanStringObject() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testEnsureBooleanStringObjectObject() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testNotNullObjectArray() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testNotNullObjectString() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testNotReady() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testNotSupported() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testNotExpected() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testStackTraceOf() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testBenchmark() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testGetCpuMemStats() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testCreateProxy() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testImplementInterfacesObjectInvocationHandlerClassOfQArray() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testImplementInterfacesObjectInvocationHandler() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testTracer() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testShow() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testRteClassOfT() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testRteClassOfTString() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testNewInstanceClassOfT() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testNewInstanceClassOfTObjectArray() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testInitNewInstance() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testInit() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testOr() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testSchedule() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testStartMeasure() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testEndMeasure() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testPrint() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testSingleton() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testArgs() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testHasOption() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testOptionStringString() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testOptionStringLong() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testOptionStringDouble() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsEmpty() {
		eq(U.isEmpty(""), true);
		eq(U.isEmpty("a"), false);
		eq(U.isEmpty(null), true);
	}

	@Test(enabled = false)
	public void testConnect() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testListenIntF2OfVoidBufferedReaderDataOutputStream() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testListenStringIntF2OfVoidBufferedReaderDataOutputStream() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testMicroHttpServer() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testGetId() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testGetIds() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testReplace() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testManage() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testInject() {

		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testInitialize() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testInstantiate() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testAutowire() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testWireT() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testWireTObject() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testWireClassOfT() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testWireClassOfTObject() {
		fail("Not yet implemented");
	}

	@Test(enabled = false)
	public void testConvert() {
		fail("Not yet implemented");
	}

	@Test
	public void testDate() {
		eq(U.date("27.12.2001"), U.date(27, 12, 2001));
		eq(U.date("2/11.2000"), U.date(2, 11, 2000));
		eq(U.date("20-03/1984"), U.date(20, 3, 1984));
		eq(U.date("2006-12-31"), U.date(31, 12, 2006));

		Calendar cal = Calendar.getInstance();
		cal.setTime(U.date(31, 12, 2006));

		eq(cal.get(Calendar.YEAR), 2006);
		eq(cal.get(Calendar.MONTH), 11);
		eq(cal.get(Calendar.DAY_OF_MONTH), 30);

		cal.setTime(U.date("20/01"));

		eq(cal.get(Calendar.YEAR), U.thisYear());
		eq(cal.get(Calendar.MONTH), 0);
		eq(cal.get(Calendar.DAY_OF_MONTH), 19);
	}

}
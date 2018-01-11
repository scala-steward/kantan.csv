/*
 * Copyright 2015 Nicolas Rinaudo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kantan.csv
package enumeratum.values

import kantan.codecs.enumeratum.laws.discipline._
import kantan.codecs.laws.discipline.SerializableTests
import kantan.csv.enumeratum.arbitrary._
import kantan.csv.laws.discipline.{CellCodecTests, RowCodecTests}
import org.scalatest.FunSuite
import org.typelevel.discipline.scalatest.Discipline

class ShortEnumCodecTests extends FunSuite with Discipline {

  checkAll("CellEncoder[EnumeratedShort]", SerializableTests[CellEncoder[EnumeratedShort]].serializable)
  checkAll("CellDecoder[EnumeratedShort]", SerializableTests[CellDecoder[EnumeratedShort]].serializable)
  checkAll("RowEncoder[EnumeratedShort]", SerializableTests[RowEncoder[EnumeratedShort]].serializable)
  checkAll("RowDecoder[EnumeratedShort]", SerializableTests[RowDecoder[EnumeratedShort]].serializable)
  checkAll("CellCodec[EnumeratedShort]", CellCodecTests[EnumeratedShort].codec[String, Float])
  checkAll("RowCodec[EnumeratedShort]", RowCodecTests[EnumeratedShort].codec[String, Float])

}

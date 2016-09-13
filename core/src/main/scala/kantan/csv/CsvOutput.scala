/*
 * Copyright 2016 Nicolas Rinaudo
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

import java.io._
import kantan.codecs.resource.WriterResource
import kantan.csv.engine.WriterEngine

/** Type class for all types that can be turned into [[CsvWriter]] instances.
  *
  * Instances of [[CsvOutput]] are rarely used directly. The preferred, idiomatic way is to use the implicit syntax
  * provided by [[ops.csvOutput CsvOutputOps]], brought in scope by importing `kantan.csv.ops._`.
  *
  * See the [[CsvOutput companion object]] for default implementations and construction methods.
  */
trait CsvOutput[-S] extends Serializable { self ⇒
  /** Opens a `Writer` on the specified `S`. */
  def open(s: S): Writer

  /** Opens a [[CsvWriter]] on the specified `S`.
    *
    * @param s what to open a [[CsvWriter]] on.
    * @param sep column separator.
    * @param header optional header row, defaults to none.
    */
  def writer[A: RowEncoder](s: S, sep: Char, header: Seq[String] = Seq.empty)(implicit e: WriterEngine): CsvWriter[A] =
  CsvWriter(open(s), sep, header)

  /** Writes the specified collections directly in the specifie `S`.
    *
    * @param s where to write the CSV data.
    * @param rows CSV data to encode and serialise.
    * @param sep column separator.
    * @param header optional header row, defaults to none.
    */
  def write[A: RowEncoder](s: S, rows: TraversableOnce[A], sep: Char, header: Seq[String] = Seq.empty)
                          (implicit e: WriterEngine): Unit =
  writer(s, sep, header).write(rows).close()

  /** Turns a `CsvOutput[S]` into a `CsvOutput[T]`.
    *
    * This allows developers to adapt existing instances of [[CsvOutput]] rather than write one from scratch.
    * One could, for example, write `CsvInput[File]` by basing it on `CsvInput[OutputStream]`:
    * {{{
    *   def fileOutput(implicit c: scala.io.Codec): CsvOutput[File] =
    *     CsvOutput[OutputStream].contramap(f ⇒ new FileOutputStream(f, c.charSet))
    * }}}
    */
  def contramap[T](f: T ⇒ S): CsvOutput[T] = CsvOutput.from(f andThen self.open)
}

/** Provides default instances as well as instance summoning and creation methods. */
object CsvOutput {
  /** Summons an implicit instance of `CsvOutput[A]` if one can be found.
    *
    * This is simply a convenience method. The two following calls are equivalent:
    * {{{
    *   val file: CsvOutput[File] = CsvOutput[File]
    *   val file2: CsvOutput[File] = implicitly[CsvOutput[File]]
    * }}}
    */
  def apply[A](implicit oa: CsvOutput[A]): CsvOutput[A] = oa

  /** Turns the specified function into a [[CsvOutput]].
    *
    * Note that it's usually better to compose an existing instance through [[CsvOutput.contramap]] rather than create
    * one from scratch.
    */
  def from[A](f: A ⇒ Writer): CsvOutput[A] = new CsvOutput[A] {
    override def open(s: A): Writer = f(s)
  }

  @deprecated("use from instead (see https://github.com/nrinaudo/kantan.csv/issues/44)", "0.1.14")
  def apply[A](f: A ⇒ Writer): CsvOutput[A] = CsvOutput.from(f)

  // TODO: unsafe, unacceptable, what was I thinking.
  implicit def fromResource[A](implicit ra: WriterResource[A]): CsvOutput[A] =
    CsvOutput.from(a ⇒ ra.open(a).get)
}

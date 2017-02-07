/*
 * Copyright 2017 Nicolas Rinaudo
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

/** Parent type for all errors that can occur while dealing with CSV data.
  *
  * [[ReadError]] is split into two main error types:
  *  - [[DecodeError]]: errors that occur while decoding a cell or a row.
  *  - [[ParseError]]: errors that occur while parsing raw data into CSV.
  */
sealed abstract class ReadError(msg: String) extends kantan.codecs.Error(msg)

/** Parent type for all errors that can occur while decoding CSV data. */
sealed abstract class DecodeError(msg: String) extends ReadError(msg)

/** Declares all possible values of type [[DecodeError]]. */
object DecodeError {
  /** Error that occurs when attempting to access a CSV cell whose index is outside of its row's boundaries.
    *
    * @param index index that caused the issue.
    */
  final case class OutOfBounds(index: Int) extends DecodeError(s"$index is not a valid index")

  /** Error that occurs when attempting to decode a CSV cell or row into an incompatible type.
    *
    * A typical example of this would be to try and decode a CSV cell into an `Int` when its content is, say, `foobar`.
    */
  final case class TypeError(message: String) extends DecodeError(message)

  object TypeError extends kantan.codecs.ErrorCompanion("an unspecified type error occurred")(s ⇒ new TypeError(s))
}

sealed abstract class ParseError(msg: String) extends ReadError(msg)

object ParseError {
  case object NoSuchElement extends ParseError("trying to read from an empty reader")

  final case class IOError(message: String) extends ParseError(message)

  object IOError extends kantan.codecs.ErrorCompanion("an unspecified io error occurred")(s ⇒ new IOError(s))
}

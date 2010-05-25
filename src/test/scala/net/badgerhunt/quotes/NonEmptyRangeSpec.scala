package net.badgerhunt.quotes

import org.specs.Specification

class NonEmptyRangeSpec extends Specification {

  "A closed NonEmptyRange" should {
    "union with a subset range" in {
      NonEmptyRange(1, 100) union NonEmptyRange(30, 50) must_== NonEmptyRange(1, 100)
    }
    "intersect with a subset range" in {
      NonEmptyRange(1, 100) intersection NonEmptyRange(30, 50) must_== NonEmptyRange(30, 50)
    }
    "union with an open bottom range" in {
      NonEmptyRange(3, 400) union NonEmptyRange.below(300) must_== NonEmptyRange.below(400)
    }
    "intersect with an open bottom range" in {
      NonEmptyRange(3, 400) intersection NonEmptyRange.below(300) must_== NonEmptyRange(3, 300)
    }
    "union with an open top range" in {
      NonEmptyRange(33, 400) union NonEmptyRange.above(66) must_== NonEmptyRange.above(33)
    }
    "intersect with an open top range" in {
      NonEmptyRange(33, 400) intersection NonEmptyRange.above(66) must_== NonEmptyRange(66, 400)
    }
  }
  
}
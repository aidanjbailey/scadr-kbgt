package kbgt.logic

import kbgt._
import scala.collection.mutable.ListBuffer
import org.tweetyproject.logics.pl.syntax._
import org.tweetyproject.logics.pl.sat._
import org.tweetyproject.logics.pl.reasoner.SatReasoner
import scala.jdk.CollectionConverters._
import java.io.PrintWriter
import java.io.File
import scala.collection.mutable
import scala.io.Source

/** A defeasible propositional knowledge base.
  *
  * Extends [[kbgt.logic.KnowledgeBase]]
  */
class DefeasibleKnowledgeBase(formulas: DefeasibleFormula*)
    extends KnowledgeBase[DefeasibleFormula](
      mutable.Set[DefeasibleFormula](formulas: _*)
    ) {

  /** Gets the classical knowledge base materialization of the defeasible
    * knowledge base.
    *
    * @return
    *   the classical knowledge base materialization
    */
  def getMaterialization(): ClassicalKnowledgeBase =
    new ClassicalKnowledgeBase(formulas.map(f => f.getMaterialization()): _*)

  /** Loads defeasible statements from a specified file into the defeasible
    * knowledge base.
    *
    * @param filename
    *   the filename of the specified file
    */
  override def loadFile(filename: String): this.type = {
    val strings = Source
      .fromFile(filename)
      .getLines()
      .next()
      .init
      .tail
      .replaceAll("\"", "")
    if (!strings.isEmpty)
      addAll(
        strings
          .split(",")
          .map(string => Parser.parseDefeasibleFormula(string))
      )
    this
  }

  /** clone override. */
  override def clone(): DefeasibleKnowledgeBase =
    new DefeasibleKnowledgeBase(iterator.toSeq: _*)

}

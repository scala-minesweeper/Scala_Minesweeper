package de.htwg.mps.minesweeper.model.impl

import de.htwg.mps.minesweeper.model.IField

case class NumberField(isShown: Boolean, isFlagged: Boolean, numberBombs: Int) extends IField {

  def showField(): NumberField = copy(isShown = true)

  def flagField(): NumberField = copy(isFlagged = true)

  def unflagField(): NumberField = copy(isFlagged = false)

  def setNumber(number: Int): NumberField = copy(numberBombs = number)

  override def isBomb: Boolean = false

  override def toString: String = "" + numberBombs

  def incrementNumberBombsBeside(): NumberField = copy(numberBombs = numberBombs + 1)

}

object NumberField {
  def apply(): NumberField = new NumberField(isShown = false, isFlagged = false, numberBombs = 0)
}
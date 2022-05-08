package com.iverno.gustavo.movihelp.util

import java.lang.StringBuilder

class QueryGenerator {
    private var query: String = ""
    companion object{
        const val SELECT_ALL_FROM = "Select * from "
        const val WHERE           = " where "
        const val AND             = " and "
        const val OR              = " or "
        const val LIKE            = " like "
        const val ORDER_BY        = " order by "
        const val ASC             = " asc"
        const val DESC            = " desc "


    }
    private fun addElement(element: String):String{
        return StringBuilder().append(this.query)
                              .append(element)
                            .toString()
    }
    fun selecAllFrom():QueryGenerator{
        this.query = addElement(SELECT_ALL_FROM)
        return this

    }
    fun tablaName(tableName: String):QueryGenerator{
        this.query = addElement(tableName)
        return this

    }
    fun where():QueryGenerator{
        this.query = addElement(WHERE)
        return this
    }
    fun fieldName(field: String):QueryGenerator{
        this.query = addElement(field)
        return this

    }

    fun contains(parameter: String):QueryGenerator{
        this.query = StringBuilder().append(this.query)
                                    .append(LIKE)
                                    .append("'")
                                    .append("%")
                                    .append(parameter)
                                    .append("%")
                                    .append("'")
                                    .toString()
        return this

    }

    fun and():QueryGenerator{
        this.query = addElement(AND)
        return this

    }
    fun or():QueryGenerator{
        this.query = addElement(OR)
        return this
    }
    fun openParenthesis():QueryGenerator{
        this.query = addElement(" (")
        return this
    }
    fun closeParenthesis():QueryGenerator{
        this.query = addElement(") ")
        return this
    }
    fun orderBy():QueryGenerator{
        this.query = addElement(ORDER_BY)
        return this
    }
    fun asc():QueryGenerator{
        this.query = addElement(ASC)
        return this
    }
    fun desc():QueryGenerator{
        this.query = addElement(DESC)
        return this
    }
    override fun toString():String{
        return this.query
    }
}
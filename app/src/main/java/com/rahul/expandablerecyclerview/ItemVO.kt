package com.rahul.expandablerecyclerview

data class ItemVO(val title: String, val subItemsList: List<SubItemVO>)
data class SubItemVO(val name: String)
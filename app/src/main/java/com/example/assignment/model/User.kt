package com.example.assignment.model

class User {
    var name: String?="Anonymous"
    var email: String?=null
    var id: String?=null
    var mobilenumber:String?=null
var otp:String="0"
    constructor(){}

    constructor(name: String?, email:String?,uid:String?,mobilenumber:String,otp:String){
        this.name=name
        this.email=email
        this.id=uid
        this.mobilenumber=mobilenumber
        this.otp=otp
    }
}
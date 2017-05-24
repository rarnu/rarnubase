package com.rarnu.base.component.dns.record

import com.rarnu.base.component.dns.DNSInputStream
import com.rarnu.base.component.dns.DNSRR

/**
 * Created by rarnu on 4/8/16.
 */
class MailExchanger : DNSRR() {

    private var _preference = 0
    private var _mx: String? = null

    override fun decode(dnsIn: DNSInputStream?) {
        _preference = dnsIn!!.readShort()
        _mx = dnsIn.readDomainName()
    }

    fun getMX(): String? = _mx

    fun getPreference(): Int = _preference

    override fun toString(): String = getRRName() + "\tpreference = $_preference, mail exchanger = $_mx"
}
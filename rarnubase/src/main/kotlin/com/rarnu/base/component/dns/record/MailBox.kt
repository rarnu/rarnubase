package com.rarnu.base.component.dns.record

import com.rarnu.base.component.dns.DNSInputStream
import com.rarnu.base.component.dns.DNSRR

/**
 * Created by rarnu on 4/8/16.
 */
class MailBox : DNSRR() {

    private var _mailBox: String? = null

    override fun decode(dnsIn: DNSInputStream?) {
        _mailBox = dnsIn?.readDomainName()
    }

    fun getMailbox(): String? = _mailBox

    override fun toString(): String = getRRName() + "\tmailbox = $_mailBox"
}
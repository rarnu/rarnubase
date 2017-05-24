package com.rarnu.base.component.dns.record

import com.rarnu.base.component.dns.DNSInputStream
import com.rarnu.base.component.dns.DNSRR

/**
 * Created by rarnu on 4/8/16.
 */
class MailRename : DNSRR() {

    private var _mailRename: String? = null

    override fun decode(dnsIn: DNSInputStream?) {
        _mailRename = dnsIn?.readDomainName()
    }

    fun getMailRename(): String? = _mailRename

    override fun toString(): String = getRRName() + "\tmail rename = $_mailRename"
}
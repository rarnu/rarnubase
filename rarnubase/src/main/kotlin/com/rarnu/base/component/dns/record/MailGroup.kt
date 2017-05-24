package com.rarnu.base.component.dns.record

import com.rarnu.base.component.dns.DNSInputStream
import com.rarnu.base.component.dns.DNSRR

/**
 * Created by rarnu on 4/8/16.
 */
class MailGroup : DNSRR() {

    private var _mailGroup: String? = null

    override fun decode(dnsIn: DNSInputStream?) {
        _mailGroup = dnsIn?.readDomainName()
    }

    fun getMailGroup(): String? = _mailGroup

    override fun toString(): String = getRRName() + "\tmail group = $_mailGroup"
}
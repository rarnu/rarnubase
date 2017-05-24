package com.rarnu.base.component.dns.record

import com.rarnu.base.component.dns.DNSInputStream
import com.rarnu.base.component.dns.DNSRR

/**
 * Created by rarnu on 4/8/16.
 */
class CanonicalName : DNSRR() {

    private var _canonicalName: String? = null

    override fun decode(dnsIn: DNSInputStream?) {
        _canonicalName = dnsIn?.readDomainName()
    }

    fun getCanonicalName(): String? = _canonicalName

    override fun toString(): String = getRRName() + "\tcanonical name = $_canonicalName"
}
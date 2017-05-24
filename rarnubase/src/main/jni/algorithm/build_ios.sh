#!/bin/sh

FPC_ROOT=/usr/local/lib/fpc/3.1.1
FPC_A64=${FPC_ROOT}/ppcrossa64
FPC_ARM=${FPC_ROOT}/ppcrossarm
FPC_EMU=${FPC_ROOT}/ppcx64
FPC_EMU32=${FPC_ROOT}/ppc386

T_A64="darwin"
T_ARM="darwin"
T_EMU="iphonesim"
T_EMU32="iphonesim"

_clean() {
    find . -name "*.o" | xargs rm -f
    find . -name "*.ppu" | xargs rm -f
    find . -name "*.rsj" | xargs rm -f
    find . -name "link.res" | xargs rm -f
    find . -name "linksyms.fpc" | xargs rm -f
    find . -name "ppas.sh" | xargs rm -f
}

_compile() {
	ARCH=$1
	CMD="FPC_${ARCH}"
    TARGET="T_${ARCH}"
    ${!CMD} -T${!TARGET} -Cn -Fulockbox -Fusec -Fu../jni alg.lpr
	ar -q libalg_${ARCH}.a `grep "\.o$" link.res`
	ranlib libalg_${ARCH}.a 
}

rm libhjalg.a

#compile
_clean
_compile "A64"
_clean
_compile "ARM"
_clean
_compile "EMU"
_clean
_compile "EMU32"

# combine
lipo -create libalg_A64.a libalg_ARM.a libalg_EMU.a libalg_EMU32.a -output libalg.a

# clean
_clean
rm libalg_A64.a
rm libalg_ARM.a
rm libalg_EMU.a
rm libalg_EMU32.a


#!/bin/sh

ROOT_PATH=/usr/local/codetyphon
TYPHON_PATH=${ROOT_PATH}/typhon
TYPHON_BIN_LIB=${ROOT_PATH}/binLibraries
FPC=/usr/local/codetyphon/fpc/fpc64/bin/x86_64-linux/fpc

P_ARM=arm
P_MIPS=mipsel
P_X86=i386

LIB_ARM=arm
LIB_MIPS=mips
LIB_X86=i386

_clean() {
    find . -name "*.o" | xargs rm -f
    find . -name "*.ppu" | xargs rm -f
}

_compile() {
	CPU=$1
	LIB=$2
	INSTALL=$3
	_env ${CPU}
	${FPC} -B -Tandroid -P${CPU} -MObjFPC -Scghi -O3 -vewnhibq \
		-Fl${TYPHON_BIN_LIB}/android-4.4-api19-${LIB} \
		-Fu../jni \
		-Fuz \
		-Fu3rd/bzip2 \
		-Fu3rd/exlz \
		-Fu. \
		-Fu${TYPHON_PATH}/components/BaseUtils \
		-Fu${TYPHON_PATH}/packager/units/${CPU}-android \
		-olibcompress_${CPU}.so \
	compress.lpr
}

_compile $P_ARM $LIB_ARM "armeabi"
_clean
# _compile $P_MIPS $LIB_MIPS "mips"
_compile $P_X86 $LIB_X86 "x86"
_clean


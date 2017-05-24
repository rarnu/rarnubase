unit hjsec_elf;

{$mode objfpc}{$H+}

interface

uses
  Classes, SysUtils, JNI2, hjlockbox;

function elfEncryptString(str: PChar): PChar; cdecl;
function Java_com_rarnu_base_security_AlgorithmUtils_elfEncryptString(env: PJNIEnv; obj: jobject; str: jstring): jstring; stdcall;

implementation

function _elfEncryptString(str: PChar): PChar;
var
  d: LongInt;
  ret: string;
begin
  try
    StringHashELF(d, string(str));
    ret := BufferToHex(d, SizeOf(d));
    Result := StrAlloc(Length(ret));
    strcopy(Result, PChar(ret));
  except
    Result := '';
  end;
end;

function elfEncryptString(str: PChar): PChar; cdecl;
begin
  Result := _elfEncryptString(str);
end;

function Java_com_rarnu_base_security_AlgorithmUtils_elfEncryptString(
  env: PJNIEnv; obj: jobject; str: jstring): jstring; stdcall;
var
  ret: PChar;
begin
  ret  := _elfEncryptString(PChar(TJNIEnv.JStringToString(env, str)));
  Result := TJNIEnv.StringToJString(env, string(ret));
end;

end.


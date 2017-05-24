unit hjsec_sha1;

{$mode objfpc}{$H+}

interface

uses
  Classes, SysUtils, JNI2, hjlockbox, hjfilestream_utils;

function sha1EncryptString(str: PChar): PChar; cdecl;
function sha1EncryptFile(filePath: PChar): PChar; cdecl;
function Java_com_rarnu_base_security_AlgorithmUtils_sha1EncryptString(env: PJNIEnv; obj: jobject; str: jstring): jstring; stdcall;
function Java_com_rarnu_base_security_AlgorithmUtils_sha1EncryptFile(env: PJNIEnv; obj: jobject; filePath: jstring): jstring; stdcall;

implementation

var
  Buffer: array[0..1023] of Byte;

function _sha1EncryptString(str: PChar): PChar;
var
  d: TSHA1Digest;
  ret: string;
begin
  try
    StringHashSHA1(d, string(str));
    ret := BufferToHex(d, SizeOf(d));
    Result := StrAlloc(Length(ret));
    strcopy(Result, PChar(ret));
  except
    Result := '';
  end;
end;

function _sha1EncryptFile(filePath: PChar): PChar;
var
  s: TStream;
  c: TSHA1Context;
  d: TSHA1Digest;
  ret: string;
  bs: Int64;
begin
  try
    ret := '';
    s := openFileStream(string(filePath));
    if Assigned(s) then begin
      InitSHA1(c);
      bs := s.Read(Buffer, SizeOf(Buffer));
      while (bs > 0) do begin
        UpdateSHA1(c, Buffer, bs);
        bs := s.Read(Buffer, SizeOf(Buffer));
      end;
      FinalizeSHA1(c, d);
      closeFileStream(s);
      ret := BufferToHex(d, SizeOf(d));
    end;
    Result := StrAlloc(Length(ret));
    strcopy(Result, PChar(ret));
  except
    Result := '';
  end;
end;

function sha1EncryptString(str: PChar): PChar; cdecl;
begin
  Result := _sha1EncryptString(str);
end;

function sha1EncryptFile(filePath: PChar): PChar; cdecl;
begin
  Result := _sha1EncryptFile(filePath);
end;

function Java_com_rarnu_base_security_AlgorithmUtils_sha1EncryptString(
  env: PJNIEnv; obj: jobject; str: jstring): jstring; stdcall;
var
  ret: PChar;
begin
  ret  := _sha1EncryptString(PChar(TJNIEnv.JstringToString(env, str)));
  Result := TJNIEnv.StringToJString(env, string(ret));
end;

function Java_com_rarnu_base_security_AlgorithmUtils_sha1EncryptFile(
  env: PJNIEnv; obj: jobject; filePath: jstring): jstring; stdcall;
var
  ret: PChar;
begin
  ret  := _sha1EncryptFile(PChar(TJNIEnv.JStringToString(env, filePath)));
  Result := TJNIEnv.StringToJString(env, string(ret));
end;

end.


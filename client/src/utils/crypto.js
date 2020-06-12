// 密码加密工具

import CryptoJS from 'crypto-js'

// 十六位十六进制数作为密钥
const key = CryptoJS.enc.Utf8.parse('3245ba918f699aed');
// 十六位十六进制数作为偏移量
const iv = CryptoJS.enc.Utf8.parse('1146ce91c0daf452');

// 加密方法
function encrypt (word) {
    let src = CryptoJS.enc.Utf8.parse(word);
    let encrypted = CryptoJS.AES.encrypt(src, key, {
        iv: iv,
        mode: CryptoJS.mode.CBC,
        padding: CryptoJS.pad.Pkcs7
    });
    return encrypted.ciphertext.toString().toUpperCase();
}

// 解密方法
function decrypt (word) {
    let encryptedHexStr = CryptoJS.enc.Hex.parse(word);
    let src = CryptoJS.enc.Base64.stringify(encryptedHexStr);
    let decrypt = CryptoJS.AES.decrypt(src, key, {
        iv: iv,
        mode: CryptoJS.mode.CBC,
        padding: CryptoJS.pad.Pkcs7
    });
    let decryptedStr = decrypt.toString(CryptoJS.enc.Utf8);
    return decryptedStr.toString();
}

export default {
    encrypt,
    decrypt
}

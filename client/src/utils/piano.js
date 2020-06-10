import a48 from '@/assets/piano_sounds/a48.mp3';
import a49 from '@/assets/piano_sounds/a49.mp3';
import a50 from '@/assets/piano_sounds/a50.mp3';
import a51 from '@/assets/piano_sounds/a51.mp3';
import a52 from '@/assets/piano_sounds/a52.mp3';
import a53 from '@/assets/piano_sounds/a53.mp3';
import a54 from '@/assets/piano_sounds/a54.mp3';
import a55 from '@/assets/piano_sounds/a55.mp3';
import a56 from '@/assets/piano_sounds/a56.mp3';
import a57 from '@/assets/piano_sounds/a57.mp3';
import a65 from '@/assets/piano_sounds/a65.mp3';
import a66 from '@/assets/piano_sounds/a66.mp3';
import a67 from '@/assets/piano_sounds/a67.mp3';
import a68 from '@/assets/piano_sounds/a68.mp3';
import a69 from '@/assets/piano_sounds/a69.mp3';
import a70 from '@/assets/piano_sounds/a70.mp3';
import a71 from '@/assets/piano_sounds/a71.mp3';
import a72 from '@/assets/piano_sounds/a72.mp3';
import a73 from '@/assets/piano_sounds/a73.mp3';
import a74 from '@/assets/piano_sounds/a74.mp3';
import a75 from '@/assets/piano_sounds/a75.mp3';
import a76 from '@/assets/piano_sounds/a76.mp3';
import a77 from '@/assets/piano_sounds/a77.mp3';
import a78 from '@/assets/piano_sounds/a78.mp3';
import a79 from '@/assets/piano_sounds/a79.mp3';
import a80 from '@/assets/piano_sounds/a80.mp3';
import a81 from '@/assets/piano_sounds/a81.mp3';
import a82 from '@/assets/piano_sounds/a82.mp3';
import a83 from '@/assets/piano_sounds/a83.mp3';
import a84 from '@/assets/piano_sounds/a84.mp3';
import a85 from '@/assets/piano_sounds/a85.mp3';
import a86 from '@/assets/piano_sounds/a86.mp3';
import a87 from '@/assets/piano_sounds/a87.mp3';
import a88 from '@/assets/piano_sounds/a88.mp3';
import a89 from '@/assets/piano_sounds/a89.mp3';
import a90 from '@/assets/piano_sounds/a90.mp3';
import b49 from '@/assets/piano_sounds/b49.mp3';
import b50 from '@/assets/piano_sounds/b50.mp3';
import b52 from '@/assets/piano_sounds/b52.mp3';
import b53 from '@/assets/piano_sounds/b53.mp3';
import b54 from '@/assets/piano_sounds/b54.mp3';
import b56 from '@/assets/piano_sounds/b56.mp3';
import b57 from '@/assets/piano_sounds/b57.mp3';
import b66 from '@/assets/piano_sounds/b66.mp3';
import b67 from '@/assets/piano_sounds/b67.mp3';
import b68 from '@/assets/piano_sounds/b68.mp3';
import b69 from '@/assets/piano_sounds/b69.mp3';
import b71 from '@/assets/piano_sounds/b71.mp3';
import b72 from '@/assets/piano_sounds/b72.mp3';
import b73 from '@/assets/piano_sounds/b73.mp3';
import b74 from '@/assets/piano_sounds/b74.mp3';
import b76 from '@/assets/piano_sounds/b76.mp3';
import b79 from '@/assets/piano_sounds/b79.mp3';
import b80 from '@/assets/piano_sounds/b80.mp3';
import b81 from '@/assets/piano_sounds/b81.mp3';
import b83 from '@/assets/piano_sounds/b83.mp3';
import b84 from '@/assets/piano_sounds/b84.mp3';
import b86 from '@/assets/piano_sounds/b86.mp3';
import b87 from '@/assets/piano_sounds/b87.mp3';
import b89 from '@/assets/piano_sounds/b89.mp3';
import b90 from '@/assets/piano_sounds/b90.mp3';

import * as Tone from "tone";

let samples = {
    'E3': a48,
    'C2': a49,
    'D2': a50,
    'E2': a51,
    'F2': a52,
    'G2': a53,
    'A2': a54,
    'B2': a55,
    'C3': a56,
    'D3': a57,
    'B4': a65,
    'A6': a66,
    'F6': a67,
    'D5': a68,
    'A3': a69,
    'E5': a70,
    'F5': a71,
    'G5': a72,
    'F4': a73,
    'A5': a74,
    'B5': a75,
    'C6': a76,
    'C7': a77,
    'B6': a78,
    'G4': a79,
    'A4': a80,
    'F3': a81,
    'B3': a82,
    'C5': a83,
    'C4': a84,
    'E4': a85,
    'G6': a86,
    'G3': a87,
    'E6': a88,
    'D4': a89,
    'D6': a90,
    'C#2': b49,
    'D#2': b50,
    'F#2': b52,
    'G#2': b53,
    'A#2': b54,
    'C#3': b56,
    'D#3': b57,
    'A#6': b66,
    'F#6': b67,
    'D#5': b68,
    'A#3': b69,
    'F#5': b71,
    'G#5': b72,
    'F#4': b73,
    'A#5': b74,
    'C#6': b76,
    'G#4': b79,
    'A#4': b80,
    'F#3': b81,
    'C#5': b83,
    'C#4': b84,
    'G#6': b86,
    'G#3': b87,
    'D#4': b89,
    'D#6': b90
}

let piano = new Tone.Sampler(samples).toMaster();

function playByScoreText(scoreText) {
    let scoreArr = scoreText.split(' ');
    let sleepTime = 0;
    scoreArr.forEach(note => {
        // 取出时值
        let duration = note.substring(note.indexOf('(') + 1, note.indexOf(')'));
        duration = duration + 'n';

        // 取出音名
        let name = note.substring(0, note.indexOf('('));
        if (name !== '0') {
            // 加上当前上下文时间规划每个音符的播放时间
            piano.triggerAttackRelease(name, duration, piano.context.now() + sleepTime);
        }

        sleepTime += Tone.Time(duration);
    });
}

export default {
    samples,
    piano,
    playByScoreText
}
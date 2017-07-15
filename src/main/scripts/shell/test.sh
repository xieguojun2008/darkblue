#!/bin/sh

SSHPASS72="/ahact/applications/actcrontab/sshpass -p 4d2x@5vMH"
SSHPASS131="/ahact/applications/actcrontab/sshpass -p 3d8h#5v0M"
SSHPASS132="/ahact/applications/actcrontab/sshpass -p 3d8h#5v0M"
SSHPASS211="/ahact/applications/actcrontab/sshpass -p 3d8h#5v0M"
SSHPASS212="/ahact/applications/actcrontab/sshpass -p 3d8h#5v0M"


SSHPASS_37="/ahact/applications/actcrontab/sshpass -p 3d8h#5v0M"

LOCAL1="/ahact/applications/marketplatnew/deploy/up"
LOCAL2="/ahact/applications/marketplat/common/upload"
LOCAL3="/ahact/applications/activity/deploy"
LOCAL4="/ahact/applications/activity/ebintegral/uploads/pic"
LOCAL5="/ahact/applications/activity/deploy/up"

${SSHPASS_37} ssh ahact@10.243.37.158 -o StrictHostKeyChecking=no uname -a
${SSHPASS_37} scp -r ${LOCAL1}/* ahact@10.243.37.158:${LOCAL5}
${SSHPASS_37} scp -r ${LOCAL3}/* ahact@10.243.37.158:${LOCAL3}


${SSHPASS_37} ssh ahact@10.243.37.159 -o StrictHostKeyChecking=no uname -a
${SSHPASS_37} scp -r ${LOCAL1}/* ahact@10.243.37.158:${LOCAL5}
${SSHPASS_37} scp -r ${LOCAL3}/* ahact@10.243.37.159:${LOCAL3}

${SSHPASS_37} ssh ahact@10.243.37.160 -o StrictHostKeyChecking=no uname -a
${SSHPASS_37} scp -r ${LOCAL1}/* ahact@10.243.37.158:${LOCAL5}
${SSHPASS_37} scp -r ${LOCAL3}/* ahact@10.243.37.160:${LOCAL3}

${SSHPASS_37} ssh ahact@10.243.37.161 -o StrictHostKeyChecking=no uname -a
${SSHPASS_37} scp -r ${LOCAL1}/* ahact@10.243.37.158:${LOCAL5}
${SSHPASS_37} scp -r ${LOCAL3}/* ahact@10.243.37.161:${LOCAL3}

${SSHPASS_37} ssh ahact@10.243.37.162 -o StrictHostKeyChecking=no uname -a
${SSHPASS_37} scp -r ${LOCAL1}/* ahact@10.243.37.158:${LOCAL5}
${SSHPASS_37} scp -r ${LOCAL3}/* ahact@10.243.37.162:${LOCAL3}

${SSHPASS_37} ssh ahact@10.243.37.163 -o StrictHostKeyChecking=no uname -a
${SSHPASS_37} scp -r ${LOCAL1}/* ahact@10.243.37.158:${LOCAL5}
${SSHPASS_37} scp -r ${LOCAL3}/* ahact@10.243.37.163:${LOCAL3}




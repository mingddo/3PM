import { AuthorizationInstance } from './index.js'

const instance = AuthorizationInstance();

function getNotice(userid,success,fail){
  instance
    .get(`notice/list/${userid}`)
    .then(success)
    .catch(fail)
}

export {
  getNotice
}
import { AuthorizationInstance } from './index.js'

const instance = AuthorizationInstance();

function getNotice(userid,pk,success,fail){
  instance
    .get(`notice/list/${userid}`, {
      params: {
        startNum: pk
      }
    })
    .then(success)
    .catch(fail)
}

export {
  getNotice
}
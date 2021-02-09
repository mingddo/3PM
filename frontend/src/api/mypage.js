import { AuthorizationInstance } from './index.js'

const instance = AuthorizationInstance();

function getprofileInfo(user_id,success,fail){
  instance
    .get(`users/${user_id}`)
    .then(success)
    .catch(fail)
}

function getprofileFeed(userid, pk,success,fail){
  instance
    .get(`indoors/user/${userid}`, {
      params: {
        startNum: pk
      }
    } )
    .then(success)
    .catch(fail)
}

export {
  getprofileInfo,getprofileFeed
}
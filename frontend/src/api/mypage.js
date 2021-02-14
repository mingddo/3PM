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

function followToggle(userid,success,fail){
  instance
    .get(`users/${userid}/follow`)
    .then(success)
    .catch(fail)
}

function followerList(userid,success,fail){
  instance
    .get(`users/${userid}/follower`)
    .then(success)
    .catch(fail)
}

function followingList(userid,success,fail){
  instance
    .get(`users/${userid}/following`)
    .then(success)
    .catch(fail)
}


export {
  getprofileInfo,getprofileFeed, followToggle, followerList, followingList 
}
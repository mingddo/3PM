import { AuthorizationInstance } from './index.js'

const instance = AuthorizationInstance();

function getprofileInfo(user_id,success,fail){
  instance
    .get(`users/${user_id}`)
    .then(success)
    .catch(fail)
}
// 해당 유저 작성한 꽃보다집 전체 조회
function getprofileFeedIndoor(userid, pk,success,fail){
  instance
    .get(`indoors/user/${userid}`, {
      params: {
        startNum: pk
      }
    } )
    .then(success)
    .catch(fail)
}
// 유저가 그룹 카테고리에서 작성한 피드 리스트 가져오기
function getprofileFeedGroup(userid,pk,success,fail){
  instance
    .get(`users/${userid}/groups/feeds`, {
      params: {
        startNum: pk
      }
    } )
    .then(success)
    .catch(fail)
}
// 해당 유저 작성한 청산별곡 전체 조회
function getprofileFeedOutdoor(userid,pk,success,fail){
  instance
    .get(`outdoors/user/${userid}`, {
      params: {
        startNum: pk
      }
    } )
    .then(success)
    .catch(fail)
}
// 해당 유저 작성한 워커홀릭 전체 조회
function getprofileFeedWorker(userid, pk,success,fail){
  instance
    .get(`worker/user/${userid}`, {
      params: {
        startNum: pk
      }
    } )
    .then(success)
    .catch(fail)
}
// 해당 유저가 가입한 그룹 정보 보여주기
function getprofileGroups(userid,success,fail) {
  instance
  .get(`users/${userid}/groups`)
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

function userInfoDetail(userid,success,fail){
  instance
    .get(`users/${userid}/detail`)
    .then(success)
    .catch(fail)
}

function editUserInfo(userid, userinfo, success,fail){
  instance
    .put(`users/${userid}`, userinfo)
    .then(success)
    .catch(fail)
}


export {
  getprofileInfo,
  getprofileFeedIndoor, 
  getprofileFeedOutdoor,
  getprofileFeedGroup,
  getprofileFeedWorker,
  getprofileGroups,
  followToggle, 
  followerList, 
  followingList, 
  userInfoDetail, 
  editUserInfo
}
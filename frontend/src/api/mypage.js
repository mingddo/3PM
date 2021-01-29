import { createInstance } from './index.js'

const instance = createInstance();

function getprofileInfo(user_id,success,fail){
  instance
    .get(`mypage/${user_id}`)
    .then(success)
    .catch(fail)
}

function getprofileFeed(id,num,success,fail){
  instance
    .get(`feed/list/${id}/${num}`)
    .then(success)
    .catch(fail)
}




export {
  getprofileInfo,getprofileFeed
}
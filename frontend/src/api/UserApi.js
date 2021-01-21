/*
 User API 예시
 */

const requestLogin = (data,callback,errorCallback) => {
    //백앤드와 로그인 통신하는 부분
    callback();

}

const UserApi = {
    requestLogin:(data,callback,errorCallback)=>requestLogin(data,callback,errorCallback)
}

export default UserApi

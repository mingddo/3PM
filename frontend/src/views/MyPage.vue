<template>
  <section class="myPagemainSection">
    <header class="myPageHeader">
      <!-- 프로필 정보들 -->
      <div class="profileInfo1">
        <!-- 커버사진 및 본인 프로필 사진, 이름 -->
        <div class="profileImgFrame">
          <img
            class="pofileImg"
            src="https://lh3.googleusercontent.com/proxy/nhF8zAo7bmBI0DmEaDB5z45y60SaBo8ikkatFdGOl3K5bPkz3kzq2r-WHxN-ry3WBxgKeujWotOlXGg"
            alt="프로필사진"
          />
        </div>
        <h1>{{ userinfo.name }}</h1>
      </div>
      <hr class="myPageHeaderhr" />
      <div class="profileInfo2">
        <!-- 팔로우, 팔로윙, 그룹, ... 프로필 수정하기 버튼 -->
        <div class="profileDetailInfo">
          <h3>내가 추가한 친구</h3>
          <div>{{ userinfo.following }}</div>
        </div>
        <div class="profileDetailInfo">
          <h3>나를 추가한 친구</h3>
          <div>{{ userinfo.following }}</div>
        </div>
        <div class="profileDetailInfo">
          <h3>내가 속한 그룹</h3>
          <div>{{ userinfo.group }}</div>
        </div>
      </div>
      <div class="settings">설정 변경</div>
    </header>
    <article class="myPagearticle">
      <!-- 최근활동 -->
      <section>
        <h2>최근 활동</h2>
        <div>
          <!-- 최근활동 3개? 미리보기 -->
        </div>
      </section>
      <!-- 피드 -->
      <section>
        <!-- 게시물, 필터 들어가는 부분 -->
        <header>
          <h2>게시물</h2>
          <div>필터</div>
          <div>
            <div>모두</div>
            <div>꽃보다 집</div>
          </div>
        </header>
        <!-- 게시물들이 나옴 컴포넌트들이 v-for돌기, 무한스크롤로 내릴 수 있음 -->

        <article></article>
      </section>
    </article>
  </section>
</template>

<script>
export default {
  data() {
    return {
      userinfo: {
        name: "명도균",
        following: 100,
        follow: 1000,
        group: 4,
        feed: [{}, {}],
      },
    };
  },
};
</script>

<style scoped>
* {
  box-sizing: border-box;
}
.myPageHeader {
  width: 100%;
  height: auto;
  padding: 25px 0;
  background-color: #ffffff;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
}

.profileInfo1 {
  width: 940px;
  margin: auto;
  padding: 15px 0;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
}
.profileInfo2 {
  width: 940px;
  margin: auto;
  display: flex;
  justify-content: center;
  align-items: center;
}

.profileDetailInfo {
  height: 60px;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
  margin: 30px;
}

.myPageHeaderhr {
  width: 940px;
  height: 1px;
  border: none;
  background-color: rgba(0, 0, 0, 0.2);
  box-shadow: 0 0.5px 0.5px rgba(0, 0, 0, 0.2);
}

.settings {
  width: 150px;
}
.myPagearticle {
  width: 100%;
  height: auto;
  padding: 15px 0;
  background-color: #f0f2f5;
}
.myPagemainSection {
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
}
.profileImgFrame {
  width: 150px;
  height: 150px;
  border: solid 1px rgba(0, 0, 0, 0.2);
  border-radius: 70%;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.2);
  overflow: hidden;
  display: flex;
  justify-content: center;
}

.pofileImg {
  width: 200px;
  height: 150px;
  object-fit: cover;
}
</style>

<template>
    <article class="feed-comment-nested">
      <div class="feed-comment-nested-userprofile-box">
        <div class="feed-comment-nested-userprofile">
          <img
            src="https://blog.cpanel.com/wp-content/uploads/2019/08/user-01.png"
            alt="유저프로필이미지"
            class="feed-comment-nested-userprofile-img"
            @click="goToProfile"
          >
          <div class="feed-comment-nested-userprofile-content">
            <h3 class="feed-comment-nested-userprofile-name" @click="goToProfile">{{ comment.user.nickname }}</h3>
            <p class="feed-comment-nested-userprofile-date">{{ comment.date }} </p>
          </div>
        </div>
        <div>
          <i class="fas fa-bars feed-comment-nested-btn-drop" @click="openModiDeleteBtn"></i>
          <div v-show="!foldModiDrop" class="feed-comment-nested-btn-drop-content">
            <div>
              <i class="fas fa-edit feed-comment-nested-btn-modi" @click="changeCommentModiForm">수정</i> 
            </div>
            <div>
              <i class="fas fa-trash-alt feed-comment-nested-btn-delete" @click="deleteComment">삭제</i> 
            </div>
            
          </div>
        </div>
      </div>

    <div class="feed-comment-nested-content-box" v-if="!modiForm">
      {{ commentForComment }}
    </div>
    <div v-else class="feed-comment-nested-modiInput-box">
      <input class="feed-comment-nested-modiInput" type="text" v-model="commentForComment" @keyup.enter="modiComment">
      <button class="feed-comment-nested-modiInput-btn" @click="modiComment">
        <i class="fas fa-check"></i>
      </button>
    </div>

    <!--댓글 작성자 본인의 경우 // vuex 저장 내용으로 user 정보 비교하여 확인-->

    <div class="feed-comment-nested-like-btn">
      <i class="far fa-thumbs-up" @click="likeComment"></i>
      {{ comment.like.length }}
    </div>

  </article>
</template>

<script>
export default {
  name: 'NewsFeedCommentNested',
  props: {
    comment: Object,
  },
  data() {
    return {
      modiForm: false,
      commentForComment : this.comment.content,
      foldModiDrop: true,
    };
  },
  methods: {
    goToProfile () {
      this.$router.push({ name: 'MyPage', query: { name: this.comment.user.nickname}})
    },
    likeComment () {
      // 댓글 좋아요 axios 요청
      // alert(`${this.comment.content}를 좋아합니다!`)
      Swal.fire(`${this.comment.content}를 좋아합니다!`, '', 'info');
    },
    deleteComment () {
      // 유저 본인 맞는 지 확인 한 번 하고
      const answer = window.confirm('댓글을 삭제하시겠습니까?')
      if (answer) {
        // axios DELETE 요청으로 삭제하기
      }
    },
    changeCommentModiForm () {
      this.foldModiDrop = true;
      this.modiForm = true;
    },
    modiComment () {
      this.foldModiDrop = true;
      this.modiForm = false;
      // axios put 요청으로 수정하기
    },
    openModiDeleteBtn () {
      this.foldModiDrop = !this.foldModiDrop
    },
  },
};
</script>

<style scoped>
.feed-comment-nested {
  margin: 20px;
}
.feed-comment-nested-userprofile-box {
  display: flex;
  justify-content: space-between;
}
.feed-comment-nested-userprofile {
  display: flex;
  justify-content: left;
}
.feed-comment-nested-userprofile-img {
  width: 5%;
  cursor: pointer;
}
.feed-comment-nested-userprofile-content {
  text-align: left;
  margin: 0px;
  margin-left: 10px;
}
.feed-comment-nested-userprofile-name {
  margin: 0px;
  cursor: pointer;
}
.feed-comment-nested-userprofile-date {
  margin: 0px;
}
.feed-comment-nested-btn-drop {
  cursor: pointer;
  position: relative;
}
.feed-comment-nested-btn-drop-content {
  text-align: center;
  z-index: 2;
  position: absolute;
  background-color: rgb(236, 236, 236);
  padding: 10px;
  border-radius: 5px;
}
.feed-comment-nested-btn-modi {
  padding: 10px;
  border-bottom: 1px solid rgb(155, 155, 155);;
  cursor: pointer;
}
.feed-comment-nested-btn-delete {
  padding: 10px;
  cursor: pointer;
}
.feed-comment-nested-btn-modi:hover {
  background-color: rgb(202, 202, 202) ;
  border-radius: 5px;;
}
.feed-comment-nested-btn-delete:hover {
  background-color: rgb(202, 202, 202) ;
  border-radius: 5px;;
}
.feed-comment-nested-content-box {
  margin: 10px;
  padding: 10px;
  text-align: left;
}
.feed-comment-nested-modiInput-box {
  display: flex;
  margin: 10px;
  width: 100%;
  padding: 5px;
}
.feed-comment-nested-modiInput {
  margin: 5px;
  width: 95%;
  padding: 5px;
}
.feed-comment-nested-modiInput-btn {
  margin: auto;
  padding: 10px;
  width: 10%;
}
.feed-comment-nested-like-nested {
  display: flex;
}
.feed-comment-nested-like-nested > div {
  cursor: pointer;
  margin: 10px;
}
.feed-comment-nested-like-btn {
  cursor: pointer;
  margin: 10px;
  text-align: left;
}
</style>
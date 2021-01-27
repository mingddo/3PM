<template>
    <div>

      <div @click="goToProfile">
        <!-- <img
          src="https://blog.cpanel.com/wp-content/uploads/2019/08/user-01.png"
          alt="유저프로필이미지"
        > -->
        <h3>{{ comment.user.nickname }}</h3>
      </div>

    <div v-if="!modiForm">
      {{ commentForComment }}
    </div>
    <div v-else>
      <input type="text" v-model="commentForComment" @keyup.enter="modiComment">
      <button @click="modiComment">수정완료</button>
    </div>

    <!--댓글 작성자 본인의 경우 // vuex 저장 내용으로 user 정보 비교하여 확인-->
    <div v-if="!modiForm">
      <button @click="changeCommentModiForm">수정</button>
      <button @click="deleteComment">삭제</button>
    </div>

    <div>
      <i class="far fa-thumbs-up" @click="likeComment"></i>
      {{ comment.like.length }}
    </div>

  </div>
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
    };
  },
  methods: {
    goToProfile () {
      this.$router.push({ name: 'MyPage', query: { name: this.comment.user.nickname}})
    },
    likeComment () {
      // 댓글 좋아요 axios 요청
      alert(`${this.comment.content}를 좋아합니다!`)
    },
    deleteComment () {
      // 유저 본인 맞는 지 확인 한 번 하고
      const answer = window.confirm('댓글을 삭제하시겠습니까?')
      if (answer) {
        // axios DELETE 요청으로 삭제하기
      }
    },
    changeCommentModiForm () {
      this.modiForm = true;
    },
    modiComment () {
      this.modiForm = false;
      // axios put 요청으로 수정하기
    },
  },
};
</script>

<style lang="scss" scoped>

</style>
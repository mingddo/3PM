<template>
  <div>
    <div class="feed-detail-comment-input-box">
      <NewsFeedProfile 
        class="feed-detail-comment-img"
        :proImg="myImg ? myImg : defaultImg"
        :userId="userpk"
      />

      <Mentionable
        :keys="['@']"
        :items="items"
        offset="6"
      >
        <textarea class="feed-comment-input" placeholder="댓글을 입력해주세요" autocomplete="false" name="comment" id="comment" cols="30" rows="10" v-model="commentInput" @input="commentKeyup" @keyup.enter="stopAuto"></textarea>
      </Mentionable>
      <button class="feed-detail-comment-btn" @click="createComment"><i class="fas fa-plus"></i></button>
    </div>
    <section>
      <div v-if="comments.length == 0" class="feed-detail-comment-empty">
        댓글이 없습니다. 댓글을 작성해주세요.
      </div>
      <div v-for="(comment, idx) in comments" :key="idx">
        <CommentItem
          :comment="comment"
          :id="id"
          :Category="Category"
          @pushUserToComment="pushUserToComment"
          @setComment="setComment(0)"
        />
        <hr>
      </div>
      <div @click="setComment(page)" v-if="!last" class="feed-detail-comment-plus">
        댓글 더보기
      </div>
    </section>
  </div>
</template>

<script>
import '@/assets/css/mention.css'
import { Mentionable } from 'vue-mention'
import { mapState } from 'vuex'
import { commentList } from '@/api/comment.js'
import { createComment } from '@/api/comment.js'
import { searchAutoUser } from '@/api/comment.js'
import { getprofileInfo } from '@/api/mypage.js'
import CommentItem from './CommentItem.vue';
import NewsFeedProfile from '../Common/NewsFeedProfile.vue'

export default {
  name: 'Comment',
  components: {
    Mentionable,
    CommentItem,
    NewsFeedProfile
  },
  props: {
    id: Number,
    Category: Number,
  },
  data() {
    return {
      items: [],
      commentInput: "",
      mention: [],
      auto:false,
      comments: [],
      page: 0,
      last: false,
      delIdx: null,
      myImg: null,
    };
  },
  mounted() {
    
  },
  created () {
    this.setComment(0);
    this.getImg();
  },
  watch : {
    commentInput : function () {
      if (this.commentInput.length > 200) {
        this.commentInput.slice(0,200)
        alert('200자 이내로 작성해주세요')
      }
    }
  },
  methods: {
    getImg () {
      getprofileInfo(
        this.userpk,
        (res) => {
          this.myImg = res.data.user_img
        },
        (err) => {
          console.log(err)
        }
      )
    },
    unshiftComment (commentId) {
      this.delIdx = this.comments.findIndex((item) => {
        return item.id == commentId
      })
      this.comments = this.comments.splice(this.delIdx, 1)
    },
    iSound(a) {
      let r = ((a.charCodeAt(0) - parseInt('0xac00', 16)) / 28 / 21);
      let t = String.fromCharCode(r + parseInt('0x1100',16));
      return t
    },
    mSound(a) {
      let r = ((a.charCodeAt(0) - parseInt('0xac00',16)) / 28) % 21;
      let t = String.fromCharCode(r + parseInt('0x1161',16));
      return t;
    },
    tSound(a) {
      let r = (a.charCodeAt(0) - parseInt('0xac00',16)) % 28;
      let t = String.fromCharCode(r + parseInt('0x11A8') - 1);
      return t;
    },
    createComment () {
      if (!this.commentInput) {
        // alert('내용을 입력해주세요')
        this.$swal.fire({
          icon: 'success',
          text: '내용을 입력해주세요',
          showConfirmButton: false,
          timer: 1500
        })
      } else {
        let results = this.commentInput.match(/@/g); 
        if (results == !null) {
          let cnt = results.length
          for (let i = 1; i <= cnt; i++) {
            let slice = this.commentInput.split('@')[i]
            let mentioned = slice.split(' ')[0]
            if (mentioned) {
              this.mention.push(mentioned)
            }
          }
        }
        createComment(
          this.id,
          { "content" : this.commentInput},
          () => {
            this.comments = [];
            this.setComment(0)
          },
          (err) => {
            console.log(err)
          }
        )
        // let mentioned = slice.split(' ')[0]
        // console.log(mentioned)
        // alert(`${this.commentInput} 내용의 댓글을 작성할게요`)
        // axios 댓글 create  요청 보내기 
        this.commentInput = ''
      }
    },
    setComment (num) {
      this.$emit("createComment")

      if (num == 0) {
        this.comments = []
      }
      commentList(
        this.id,
        num,
        (res) => {
          this.page = res.data.endNum
          let comment = res.data.comments;
          if (comment && comment.length < 10) {
            this.last = true;
          }
          for (let f of comment) {
            this.comments.push(f);
          }

        },
        (err) => {
          console.log(err)
        }
      )
    },
    pushUserToComment (user) {
      this.commentInput += `@${user} `
      let input = document.getElementById('comment')
      input.focus();
    },
    stopAuto () {
      this.auto = false;
    },
    commentKeyup () {
      // this.commentInput = e.target.value;
      let lastChar = this.commentInput.charAt(this.commentInput.length-1)
      if (lastChar == '@') {
        this.auto = true;
      }
      
      if (!this.auto && lastChar == '@') {
        this.auto = true;
      } else if (this.auto && lastChar == ' ') {
        this.auto = false;
      }
      if (this.auto) {
        let results = this.commentInput.match(/@/g); 
        let cnt = 0
        if (results) {
          cnt = results.length
        }
        let mentionedUser = this.commentInput.split('@')[cnt]
        if (mentionedUser) {
          searchAutoUser(
            mentionedUser,
            (res) => {
              this.items = res.data
            },
            (err) => {
              console.log(err)
            }
          )
        }
      }
    },
  },
  computed : {
    ...mapState({
      userpk : (state) => state.userId,
      defaultImg: (state) => state.defaultImg,
    })
  }
};
</script>

<style lang="scss" scoped>

</style>
<template>
  <div>
    <div class="feed-detail-comment-input-box">
      <news-feed-profile 
        class="feed-detail-comment-img"
        :proImg="defaultImg"
        :userId="userpk"
      />

      <Mentionable
        :keys="['@']"
        :items="items"
        offset="6"
      >
        <textarea class="feed-comment-input" placeholder="댓글을 입력해주세요" name="comment" id="comment" cols="30" rows="10" :value="commentInput" @keyup="commentKeyup"></textarea>
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
import { mapState } from 'vuex'
import { Mentionable } from 'vue-mention'
import { commentList } from '@/api/comment.js'
import { createComment } from '@/api/comment.js'
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
    };
  },
  mounted() {
    
  },
  created () {
    this.setComment(0);
  },
  methods: {
    createComment () {
      if (!this.commentInput) {
        alert('내용을 입력해주세요')
      } else {
        let results = this.commentInput.match(/@/g); 
        if (results == !null) {
          let cnt = results.length
          for (let i = 1; i <= cnt; i++) {
            console.log(i)
            let slice = this.commentInput.split('@')[i]
            let mentioned = slice.split(' ')[0]
            if (mentioned) {
              this.mention.push(mentioned)
            }
          }
          console.log(this.mention)
        }
        if (this.Category == 1) {
          createComment(
            this.id,
            { "content" : this.commentInput},
            (r) => {
              console.log(r)
              this.setComment(0)
            },
            (err) => {
              console.log(err)
            }
          )
        }
        // let mentioned = slice.split(' ')[0]
        // console.log(mentioned)
        // alert(`${this.commentInput} 내용의 댓글을 작성할게요`)
        // axios 댓글 create  요청 보내기 
        this.commentInput = ''
      }
    },
    setComment (num) {
      if (this.Category == 1) {
        commentList(
          this.id,
          num,
          (res) => {
            this.page = res.data.endNum
            if (num == 0) {
              this.comments = []
            }
            let comment = res.data.comments;
            console.log(comment)
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
      }
    },
    pushUserToComment (user) {
      this.commentInput += `@${user} `
      let input = document.getElementById('comment')
      input.focus();
    },
    commentKeyup (e) {
      this.commentInput = e.target.value;
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
        let saveMention = mentionedUser.split(' ')
        if (saveMention.length > 1 || e.code == 'Space' || e.code == 'Enter') {
          this.auto = false;
        } else if (e.key !== "ArrowDown" && e.key !== "ArrowUp" && e.key !== "ArrowLeft" && e.key !== "ArrowRight") {
          console.log('api요청 단어', mentionedUser)
          // mentionedUser 을 db에 요청하여, 관련된 문자로 시작되는 단어 리스트를 받아 this.itmes 에 저장한다.
          // 현재는 백 api가 설정되어있지 않으므로 items를 직접 설정한다.
          this.items = [
            {
              value: '1',
              label: '장수민',
            },
            {
              value: '2',
              label: '잔수민',
            },
            {
              value: '3',
              label: '자스민',
            },
            {
              value: '4',
              label: '장슈민',
            },
            {
              value: '5',
              label: '장수밍',
            },
          ]
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
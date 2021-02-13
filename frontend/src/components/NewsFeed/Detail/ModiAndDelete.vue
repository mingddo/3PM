<template>
  <div>
    <button @click="changeModiForm">
      수정하기
    </button>
    <button @click="deleteFeed">
      삭제하기
    </button>
  </div>
</template>

<script>
import { deleteFeed } from '@/api/feed.js'
import { mapState } from 'vuex'

export default {
  name: 'ModiAndDelete',
  props: {
    fd: Object,
    Category: Number,
  },
  data() {
    return {
      
    };
  },
  mounted() {
    
  },
  methods: {
    changeModiForm () {
      this.$router.push({ name: 'NewsfeedForm', query: { Category: this.Category }, params: { type: 'MODI', feed: this.fd }})
    },
    deleteFeed () {
      if (this.userpk == this.fd.user.id) {
        const answer = window.confirm('정말로 삭제하시겠습니까?')
        if (answer) {
          if (this.Category == 1) {
            console.log(this.fd.id)
            deleteFeed(
              this.fd.id,
              () => {
                this.$router.push({name: 'NewsfeedPersonal', query: { Category: 1}})
              },
              (err) => {
                console.log(err)
              }
            )
          } else if (this.Category == 2) {
            // 핵인싸 DELETE 요청
          } else if (this.Category == 3) {
            // 청산별고 DELETE 요청
          } else if (this.Category == 4) {
            // 워커홀릭 DELETE 요청
          }
        }
      }
    },
  },
  computed: {
    ...mapState({
      userpk : (state) => state.userId,
    })
  },
};
</script>

<style lang="scss" scoped>

</style>
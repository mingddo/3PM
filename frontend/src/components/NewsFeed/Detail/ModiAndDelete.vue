<template>
  <div>
    <button v-if="!isShare" @click="changeModiForm">
      수정하기
    </button>
    <button @click="deleteFeed">
      삭제하기
    </button>
  </div>
</template>

<script>
import { deleteIndoors } from '@/api/indoors.js'
import { deleteGroupFeed } from '@/api/group.js'
import { deleteOutdoors } from '@/api/outdoors.js'
import { deleteWorker } from '@/api/worker.js'
import { mapState } from 'vuex'

export default {
  name: 'ModiAndDelete',
  props: {
    fd: Object,
    Category: Number,
    isShare: Boolean,
  },
  data() {
    return {
      
    };
  },
  mounted() {
    
  },
  methods: {
    changeModiForm () {
      if (this.Category == 2) {
        this.$router.push({ name: 'NewsfeedForm', query: { Category: this.Category }, params: { type: 'MODI', feed: this.fd, group: this.fd.groupId, name: this.fd.groupName }})
      } else {
        this.$router.push({ name: 'NewsfeedForm', query: { Category: this.Category }, params: { type: 'MODI', feed: this.fd }})
      }
    },
    deleteFeed () {
      if (this.userpk == this.fd.user.id) {
        this.$swal.fire({ 
          text: '정말로 삭제하시겠습니까?', 
          icon: 'warning', 
          showCancelButton: true, 
          confirmButtonText: '삭제하기', 
          cancelButtonText: '돌아가기'
        }).then(result => {
          if (result.isConfirmed) {
            if (this.Category == 1) {
              deleteIndoors(
                this.fd.id,
                () => {
                  this.$router.push({name: 'NewsfeedPersonal', query: { Category: this.Category}})
                },
                (err) => {
                  console.log(err)
                }
              )
            } else if (this.Category == 2) {
              // 핵인싸 DELETE 요청
              deleteGroupFeed(
                this.fd.groupId,
                this.fd.id,
                () => {
                  this.$router.push({name: 'NewsfeedPersonal', query: { Category: this.Category}})
                },
                (err) => {
                  console.log(err)
                }
              )
            } else if (this.Category == 3) {
              // 청산별고 DELETE 요청
              deleteOutdoors(
                this.fd.id,
                () => {
                  this.$router.push({name: 'NewsfeedPersonal', query: { Category: this.Category}})
                },
                (err) => {
                  console.log(err)
                }
              )
            } else if (this.Category == 4) {
              // 워커홀릭 DELETE 요청
              deleteWorker(
                this.fd.id,
                () => {
                  this.$router.push({name: 'NewsfeedPersonal', query: { Category: this.Category}})
                },
                (err) => {
                  console.log(err)
                }
              )
            }
          }
        });


      //   const answer = window.confirm('정말로 삭제하시겠습니까?')
      //   if (answer) {
      //     if (this.Category == 1) {
      //       console.log('삭제할 id', this.fd.id)
      //       deleteIndoors(
      //         this.fd.id,
      //         () => {
      //           this.$router.push({name: 'NewsfeedPersonal', query: { Category: this.Category}})
      //         },
      //         (err) => {
      //           console.log(err)
      //         }
      //       )
      //     } else if (this.Category == 2) {
      //       // 핵인싸 DELETE 요청
      //       deleteGroupFeed(
      //         this.fd.groupId,
      //         this.fd.id,
      //         (res) => {
      //           console.log(res)
      //           this.$router.push({name: 'NewsfeedPersonal', query: { Category: this.Category}})
      //         },
      //         (err) => {
      //           console.log(err)
      //         }
      //       )
      //     } else if (this.Category == 3) {
      //       // 청산별고 DELETE 요청
      //       deleteOutdoors(
      //         this.fd.id,
      //         () => {
      //           this.$router.push({name: 'NewsfeedPersonal', query: { Category: this.Category}})
      //         },
      //         (err) => {
      //           console.log(err)
      //         }
      //       )
      //     } else if (this.Category == 4) {
      //       // 워커홀릭 DELETE 요청
      //       deleteWorker(
      //         this.fd.id,
      //         () => {
      //           this.$router.push({name: 'NewsfeedPersonal', query: { Category: this.Category}})
      //         },
      //         (err) => {
      //           console.log(err)
      //         }
      //       )
      //     }
      //   }
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
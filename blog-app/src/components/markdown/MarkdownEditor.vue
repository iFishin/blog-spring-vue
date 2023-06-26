<!--
 * @Date: 2023-06-22 10:17:02
 * @LastEditors: Fishing yoo725@hotmail.com
 * @LastEditTime: 2023-06-22 20:42:54
 * @FilePath: \blog-app\src\components\markdown\MarkdownEditor.vue
 * @Description: https://github.com/iFishin
-->
<template>
  <mavon-editor
    class="me-editor"
    ref="md"
    v-model="editor.value"
    @imgAdd="imgAdd"
    v-bind="editor">
  </mavon-editor>
</template>


<script>

  import {mavonEditor} from 'mavon-editor'
  import 'mavon-editor/dist/css/index.css'

  import {upload} from '@/api/upload'

  export default {
    name: 'MarkdownEditor',
    props: {
      editor: Object
    },
    data() {
      return {}
    },
    mounted() {
      this.$set(this.editor, 'ref', this.$refs.md)
    },
    methods: {
      imgAdd(pos, $file) {
        let that = this
        let formdata = new FormData();
        formdata.append('image', $file);

        upload(formdata).then(data => {
          // 第二步.将返回的url替换到文本原位置![...](./0) -> ![...](url)
          if (data.code == 0) {

            that.$refs.md.$img2Url(pos, data.data.url);
          } else {
            that.$message({message: data.msg, type: 'error', showClose: true})
          }

        }).catch(err => {
          that.$message({message: err, type: 'error', showClose: true});
        })
      }
    },
    components: {
      mavonEditor
    }
  }
</script>
<style scoped>
  .me-editor {
    z-index: 6 !important;
  }

  .v-note-wrapper.fullscreen {
    top: 60px !important
  }
</style>

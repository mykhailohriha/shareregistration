function getIndex(list, id) {
    for (var i = 0; i < list.length; i++) {
        if (list[i].id === id) {
            return i;
        }
    }

    return -1;
}

var messageApi = Vue.resource('/api/v1/share{/id}');

Vue.component('share-form', {
    props: ['shares', 'shareAttr'],
    data: function () {
        return {
            comment: '',
            id: ''
        }
    },
    watch: {
        shareAttr: function (newVal, oldVal) {
            this.comment = newVal.comment;
            this.id = newVal.id;
        }
    },
    template:
        '<div>' +
        '<input type ="text" placeholder="Write something"  v-model="comment"/>' +
        '<input type ="button" value="Save" @click="save" />' +
        '</div>',
    methods: {
        save: function () {
            var share = {comment: this.comment};

            if (this.id) {
                messageApi.update({id: this.id}, share).then(result =>
                    result.json().then(data => {
                        var index = getIndex(this.shares, data.id)
                        this.shares.splice(index, 1, data);
                        this.comment = '';
                        this.id = ''
                    }))
            } else {
                messageApi.save({}, share).then(result =>
                    result.json().then(data => {
                        this.shares.push(data);
                        this.comment = '';
                    })
                )
            }
        }
    }
});

Vue.component('share-row', {
    props: ['share', 'editMethod', 'shares'],
    template: '<div><i>({{ share.id }})</i>{{ share.comment }}' +
        '<span style="position: absolute; right: 0">' +
        '<input type="button" value="Edit" @click="edit"/>' +
        '<input type="button" value="X" @click="del"/>' +
        '</span>' +
        '</div>',
    methods: {
        edit: function () {
            this.editMethod(this.share);
        },
        del: function () {
            messageApi.remove({id: this.share.id}).then(result => {
                if (result.ok) {
                    this.shares.splice(this.shares.indexOf(this.share), 1)
                }
            })
        }
    }
});


Vue.component('shares-list', {
    props: ['shares'],
    data: function () {
        return {
            share: null
        }
    },
    template:
        '<div style="position: relative; width: 300px;">' +
        '<share-form :shares="shares" :shareAttr="share"/>' +
        '<share-row v-for="share in shares" :key="share.id" :share="share"' +
        ':editMethod="editMethod" :shares="shares"/>' +
        '</div>',
    created: function () {
        messageApi.get().then(result =>
            result.json().then(data =>
                data.forEach(share => this.shares.push(share))
            ))
    },
    methods: {
        editMethod: function (share) {
            this.share = share;
        }
    }
});

var app = new Vue({
    el: '#app',
    template: '<shares-list :shares="shares"/>',
    data: {
        shares: []
    }
});
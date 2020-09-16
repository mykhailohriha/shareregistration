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
            id: '',
            status: '',
            edrpou: '',
            quantity: '',
            value: '',
        }
    },
    watch: {
        shareAttr: function (newVal, oldVal) {
            this.comment = newVal.comment;
            this.status = newVal.status;
            this.id = newVal.id;
            this.edrpou = newVal.edrpou;
            this.quantity = newVal.quantity;
            this.value = newVal.value;
        }
    },
    template:
        '<div>' +
        '<input type ="text" placeholder="Write comment..."  v-model="comment"/>' +
        '<input type ="text" placeholder="Write status..."  v-model="status"/>' +
        '<input type ="text" placeholder="Write EDRPOU..."  v-model="edrpou"/>' +
        '<input type ="text" placeholder="Write quantity..."  v-model="quantity"/>' +
        '<input type ="text" placeholder="Write value..."  v-model="value"/>' +
        '<input type ="button" value="Save" @click="save" />' +
        '</div>',
    methods: {
        save: function () {
            var share = {comment: this.comment, status: this.status, edrpou: this.edrpou, quantity: this.quantity,
            value: this.value};

            if (this.id) {
                messageApi.update({id: this.id}, share).then(result =>
                    result.json().then(data => {
                        var index = getIndex(this.shares, data.id)
                        this.shares.splice(index, 1, data);
                        this.comment = '';
                        this.status = '';
                        this.edrpou = '';
                        this.id = '';
                        this.quantity = '';
                        this.value = '';
                    }))
            } else {
                messageApi.save({}, share).then(result =>
                    result.json().then(data => {
                        this.shares.push(data);
                        this.comment = '';
                        this.status = '';
                        this.edrpou = '';
                        this.quantity = '';
                        this.value = '';
                    })
                )
            }
        }
    }
});

Vue.component('share-row', {
    props: ['share', 'editMethod', 'shares'],
    template: '<div><i>({{ share.id }})</i>Comment: {{ share.comment }} Status: {{share.status}} EDRPOU: {{share.edrpou}} ' +
        'quantity: {{share.quantity}} value: {{share.value}} total value:{{share.totalValue}} creation date:{{share.creationDate}}'+
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
        '<div style="position: relative; width: 800px;">' +
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
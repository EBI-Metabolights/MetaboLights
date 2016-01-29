<%--
  Created by IntelliJ IDEA.
  User: kalai
  Date: 11/12/2015
  Time: 13:17
  To change this template use File | Settings | File Templates.
--%>
<div id="overrideForm">

    <validations :list="validationsComplete['entries']" :changed="changedValidations">
    </validations>

    <save-override-button :validations-changed.once='changedValidations'
                          :validations-original.once='validationsComplete'>
    </save-override-button>

</div>

<div id="overrideaction" title="Save Manual override.." style="display: none">
</div>

<div id="curatorOverrideSuccessAlert" title="Overriden validations saved" style="display: none">
</div>


<template id="curator-form">
    <div class="">
        <table id="validationTable" class="dataTable table table-striped table-bordered">
            <thead class='text_header'>
            <tr>
                <th>Condition</th>
                <th>Status</th>
                <th>Description</th>
                <th>Requirement</th>
                <th>Group</th>
                <th>Message</th>
            </tr>
            </thead>

            <tbody v-for="entry in list">
            </tr>
            <td> <span v-if="entry.passedRequirement" class="ok" v-bind:class="{ 'override' : entry.overriden }">
             <span v-if="entry.overriden">*</span>
             </span>
            <span v-else class="wrong" v-bind:class="{ 'mandatory': entry.type=='MANDATORY',
            'optional': entry.type=='OPTIONAL' }">
              <span v-if="entry.overriden">*</span></span>


            </td>

            <td>
                <div class="form-group">
                    <select class="form-control" v-model="entry.passedRequirement" @change="add(entry)">

                        <option selected="{{'PASSES' == status(entry)}}" v-bind:value='true'>PASSES</option>
                        <option selected="{{'FAILS' == status(entry)}}" v-bind:value='false'>FAILS</option>
                        <option selected="{{'INCOMPLETE' == status(entry)}}" v-bind:value='false'>INCOMPLETE</option>
                    </select>
                </div>
            </td>


            <td>{{entry.description}}</td>
            <td>{{entry.type}}</td>
            <td>{{entry.group}}</td>
            <td>{{entry.message}}</td>
            </tr>
            </tbody>
        </table>
        </div>
</template>
<script src="http://cdnjs.cloudflare.com/ajax/libs/vue/1.0.10/vue.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/vue-resource/0.1.17/vue-resource.js"></script>


<script>
    Vue.config.debug = true

    Vue.component('validations', {
                props: ['list', 'changed'],
                template: '#curator-form',
                methods: {
                    status: function (entry) {
                        var statusString = '';
                        if (entry.passedRequirement) {
                            statusString = 'PASSES';
                        }
                        else {
                            if (entry.type == 'MANDATORY') {
                                statusString = 'FAILS';
                            }
                            else {
                                statusString = 'INCOMPLETE';
                            }
                        }
                        return statusString;
                    },
                    add: function (entry) {

                        entry.overriden = 'true';

                        if (!this.has(entry)) {
                            this.changed.push(entry);
                        }
                        else {
                            this.replace(entry);
                        }

                    }
                    ,
                    has: function (entry) {
                        for (var j = 0; j < this.changed.length; j++) {
                            if (entry.id == this.changed[j].id) {
                                return true;
                            }
                        }
                        return false;

                    },
                    replace: function (entry) {
                        for (var j = 0; j < this.changed.length; j++) {
                            if (entry.id == this.changed[j].id) {
                                this.changed[j] = entry;
                            }
                        }
                    }


                }

            }
    )

    Vue.component('save-override-button', {
        template: '<button @click="saveOverride" class="submit">Save override</button>',
        props: ['validationsChanged', 'validationsOriginal'],
        methods: {
            saveOverride: function (event) {

                var output = vm.$data.validationsComplete;
                output['entries'] = vm.$data.changedValidations;

                vm.$http.post('http://localhost:8080/metabolights/webservice/study/${study.studyIdentifier}/overridevalidations',
                         JSON.stringify(output))
                        .success(function() {
                    console.log('Indexing..');
                            vm.$http.get('http://localhost:8080/metabolights/webservice/index/${study.studyIdentifier}');
                });

                var dialog = $("#curatorOverrideSuccessAlert");
                dialog.text("Click refresh to see changes");
                $(dialog).dialog({
                    modal: true,
                    buttons: {
                        "OK": function () {
                            $(this).dialog("close");
                        }
                    }
                });
            }
        }


    })


    var vm = new Vue({
        el: '#overrideForm',
        http: {
            headers: {
                'user_token': '${curatorAPIToken}'
            }

        },
        data: {
            validationsComplete: JSON.parse(`${validationJson}`),
            changedValidations: []
        },
        methods : {
               sortByKeys: function(array, key1, key2) {
                return array.sort(function(a, b) {
                    var x = a[key1]+a[key2]; var y = b[key1]+b[key2];
                    return ((x < y) ? -1 : ((x > y) ? 1 : 0));
                });
             }
        },
        ready: function () {
            for (var k = 0; k < this.validationsComplete['entries'].length; k++) {
                var currentEntry = this.validationsComplete['entries'][k];
                if (currentEntry.overriden) {
                    this.changedValidations.push(currentEntry);
                }
            }
            this.validationsComplete['entries'] = this.sortByKeys(this.validationsComplete['entries'], 'passedRequirement','type');
        }
    })

</script>

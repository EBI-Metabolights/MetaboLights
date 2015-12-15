<%--
  Created by IntelliJ IDEA.
  User: kalai
  Date: 11/12/2015
  Time: 13:17
  To change this template use File | Settings | File Templates.
--%>
<div id="overrideForm">

    <validations :list="validationsComplete['entries']" >
    </validations>

    <save-override-button :validations-completed='validationsComplete'>
    </save-override-button>

</div>




<template id="curator-form">
    <table id="validationTable" class="display clean" order="[[ 1, 'asc' ],[ 0, 'desc' ]]">
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
        <td> <span v-if="entry.passedRequirement" class="ok"></span>
            <span v-else class="wrong" v-bind:class="{ 'mandatory': entry.type=='MANDATORY',
            'optional': entry.type=='OPTIONAL' }"> </span>

            <span v-if="entry.overriden">*</span>
        </td>

        <td>
            <select class="form-control" v-model="entry.passedRequirement">

                <option selected="{{'PASSES' == status(entry)}}" v-bind:value='true'>PASSES</option>
                <option selected="{{'FAILS' == status(entry)}}" v-bind:value='false'>FAILS</option>
                <option selected="{{'INCOMPLETE' == status(entry)}}" v-bind:value='false'>INCOMPLETE</option>
            </select>
        </td>


        <td>{{entry.description}}</td>
        <td>{{entry.type}}</td>
        <td>{{entry.group}}</td>
        <td>{{entry.message}}</td>
        </tr>
        </tbody>
    </table>
</template>
<script src="http://cdnjs.cloudflare.com/ajax/libs/vue/1.0.10/vue.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/vue-resource/0.1.17/vue-resource.js"></script>


<script>
    Vue.config.debug = true

    Vue.component('validations',{
                props: ['list'],
                template: '#curator-form',
                methods:{
                    status: function(entry){
                        var statusString = '';
                        if(entry.passedRequirement){
                            statusString = 'PASSES';
                        }
                        else{
                            if(entry.type == 'MANDATORY'){
                                statusString = 'FAILS';
                            }
                            else{
                                statusString = 'INCOMPLETE';
                            }
                        }
                        return statusString;
                    }

                }

            }
    )

    Vue.component('save-override-button', {
        template: '<button @click="saveOverride" class="submit">Save override</button>',
        http: {
            headers: {
                'user_token' : '${curatorAPIToken}'
            }

        },
        props: ['validationsCompleted'],
        methods: {
            saveOverride : function(event){
                console.log(JSON.stringify(this.validationsCompleted));
                console.log('saving current data');
                this.$http.post('http://localhost:8080/metabolights/webservice/study/${study.studyIdentifier}/overridevalidations', JSON.stringify(this.validationsCompleted));
                console.log('saved current choices');
            }
        }


    })



    var vm =new Vue({
        http: {
            headers: {
                'user_token' : '3d80c1f1-397c-4b5c-8d9a-4da606466847'
            }
        },
        el: '#overrideForm',
        data: {
            validationsComplete: JSON.parse(`${validationJson}`)
        }
    })

</script>

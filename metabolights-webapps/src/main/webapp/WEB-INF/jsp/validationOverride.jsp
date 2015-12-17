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
        <td> <span v-if="entry.passedRequirement" class="ok" v-bind:class="{ 'override' : entry.overriden }">
             <span v-if="entry.overriden">*</span>
             </span>
            <span v-else class="wrong" v-bind:class="{ 'mandatory': entry.type=='MANDATORY',
            'optional': entry.type=='OPTIONAL' }">
              <span v-if="entry.overriden">*</span></span>


        </td>

        <td>
            <select class="form-control" v-model="entry.passedRequirement" @change="add(entry)">

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
                        console.log("checking...")

                        entry.overriden = 'true';

                        if (!this.has(entry)) {
                            this.changed.push(entry);
                        }
                        else {
                            this.replace(entry);
                        }
                        console.log(JSON.stringify(this.changed));

                    }
                    ,
                    has: function (entry) {
                        for (var j = 0; j < this.changed.length; j++) {
                            if (entry.id == this.changed[j].id) {
                                console.log(entry.id)
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
                console.log(JSON.stringify(output));
                vm.$http.post('http://localhost:8080/metabolights/webservice/study/${study.studyIdentifier}/overridevalidations', JSON.stringify(output));


                var dialog = $("#curatorOverrideSuccessAlert");
                dialog.text("Click refresh to see changes");
                $(dialog).dialog({
                    modal: true,
                    buttons: {
                        "OK": function () {
                            <%--window.location.href = '/metabolights/' +--%>
                            <%--''+ '${study.studyIdentifier}';--%>
                            $(this).dialog("close");
                        }
                    }
                });

                <%--var dialog = $("#overrideaction");--%>
                <%--dialog.text("Have you got all the override in one go? Doing one by one will replace the existing override.");--%>
                <%--$(dialog).dialog({--%>
                    <%--modal: true,--%>
                    <%--buttons: {--%>
                        <%--"Take me back": function () {--%>
                            <%--$(this).dialog("close");--%>
                        <%--},--%>
                        <%--"Good to go": function () {--%>
                            <%--var output = vm.$data.validationsComplete;--%>
                            <%--output['entries'] = vm.$data.changedValidations;--%>
                            <%--console.log(JSON.stringify(output));--%>
                            <%--vm.$http.post('http://localhost:8080/metabolights/webservice/study/${study.studyIdentifier}/overridevalidations', JSON.stringify(output));--%>

                            <%--$(this).dialog("close");--%>

                            <%--var dialog = $("#curatorOverrideSuccessAlert");--%>
                            <%--dialog.text("Click refresh to see changes");--%>
                            <%--$(dialog).dialog({--%>
                                <%--modal: true,--%>
                                <%--buttons: {--%>
                                    <%--"OK": function () {--%>
                                        <%--&lt;%&ndash;window.location.href = '/metabolights/' +&ndash;%&gt;--%>
                                        <%--&lt;%&ndash;''+ '${study.studyIdentifier}';&ndash;%&gt;--%>
                                        <%--$(this).dialog("close");--%>
                                    <%--}--%>
                                <%--}--%>
                            <%--});--%>
                        <%--}--%>
                    <%--}--%>
                <%--});--%>
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
        ready: function () {
            for (var k = 0; k < this.validationsComplete['entries'].length; k++) {
                var currentEntry = this.validationsComplete['entries'][k];
                if (currentEntry.overriden) {
                    this.changedValidations.push(currentEntry);
                }
            }
            console.log("copied: " + JSON.stringify(this.changedValidations));
        }
    })

</script>

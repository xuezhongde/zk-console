<!--
#
# Copyright (c) 2014, Deem Inc. All Rights Reserved.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
-->
<#import "layouts/main-layout.ftl" as main>
<@main.page>

<div class="container">
    <form method="post" action="history" role="form"  class="form-horizontal">
        <div class="row-fluid">
            <div class="col-md-12 text-center">
                <h3>Top 50 Changes</h3>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-2 control-label">
                Node History
            </label>
            <div class="col-md-8">
                <input type="text" name="historyNode" value="${historyNode}" class="form-control" required>
            </div>
            <div class="col-md-2">
                <button type="submit" name="action" value="showhistory" class="btn btn-primary">Show History</button>
            </div>
        </div>
        <div class="row-fluid">
            <div class="col-md-12 text-center">
                <table class="table table-striped">  
                    <thead>  
                        <tr>  
                            <th class="col-md-1">Id</th>  
                            <th class="col-md-2">Date</th>  
                            <th class="col-md-2">User</th>  
                            <th class="col-md-1">From</th>  
                            <th>Summary</th>  
                        </tr>  
                    </thead>  
                    <tbody>
                        <#list historyLst as history>
                        <tr>  
                            <td>${history.id}</td>  
                            <td>${history.changeDate}</td>
                            <td>${history.changeUser}</td>  
                            <td>${history.changeIp}</td>  
                            <td>${history.changeSummary}</td>
                        </tr>  
                        </#list>
                    </tbody>  
                </table>  


            </div>
        </div>
    </form>
</div>

</@main.page> 
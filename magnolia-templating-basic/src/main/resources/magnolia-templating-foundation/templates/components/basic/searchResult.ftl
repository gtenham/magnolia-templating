[#assign searchResult = model.searchResult! /]
[#assign results = searchResult.results! /]
[#assign hasResults = results?has_content /]
[#assign searchTerm = ctx.parameters.s!"" /]
[#assign maxResultsPerPage = content.maxResultsPerPage!"4" /]
[#assign queryString = "s=${searchTerm}" /]

<div>
    <h3>${model.title!} (${searchResult.totalCount!"0"})</h3>
[#if (hasResults && searchTerm?has_content)]
    <br/>
    <dl>
        [#list results as result]
            [#if (result_index < maxResultsPerPage?number)]
                [#assign contentNode = cmsfn.asContentMap(result.node) /]
                <dt><a href="${cmsfn.link(result.node)}">${contentNode.title!contentNode.navigationTitle!contentNode.@name}</a></dt>
                <dd>${cmsfn.decode(contentNode).abstract!cmsfn.decode(contentNode).description!}</dd>
            [/#if]
        [/#list]
    </dl>
[#else]
    <p>No results found.</p>
[/#if]
</div>
$(document).ready(function(){
    $("#airportsearch").on('input', function() {
        keyword = $('#airportsearch').val();

        if (keyword.length > 0) {
            $("#flights").empty();
            $("#airports").load("/airports/", {
                "keyword": keyword
            });
        }
    });

    $(document).on("click", ".flightsBtn", function() {
        var icao = $(this).data("icao");
        var name = $(this).data("name");
        $("#airportsearch").val(name);
        if (name == null) {
            $.get("/airports/" + icao, function(data, status){
                if (status == "success") {
                    $("#airportsearch").val(data.name);
                }
              });
        }
        $("#airports").empty();
        $("#flights").load("/flights/" + icao);
    });
});
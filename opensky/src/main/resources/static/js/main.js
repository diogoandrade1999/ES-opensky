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
        $("#airports").empty();
        $("#flights").load("/flights/" + icao);
    });
});
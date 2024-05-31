// ---------------------------------
// cause token is not a query string, so we need to get it from the url
// ---------------------------------
const getUrlParameter = (name: string, search: string) => {
  name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
  const regex = new RegExp("[\\?&]" + name + "=([^&#]*)");
  const results = regex.exec(search);
  return results === null
    ? ""
    : decodeURIComponent(results[1].replace(/\+/g, " "));
};

export { getUrlParameter };

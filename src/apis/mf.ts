const fetchTopBottom5 = async (url: string) => {
  const res = await fetch(url, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
  });

  return res.json();
};
export default fetchTopBottom5;

// Change optional obj to mandatory later
export const fetchMFDetails = async (url: string, obj = { schemaId: 1 }) => {
  const res = await fetch(url, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(obj),
  });

  return res.json();
};

export const fetchAddToWishlist = async (
  url: string,
  obj: {
    token: string | null;
    mfSchemaId: number;
    mfName: string;
    mfFundHouse: string;
  }
) => {
  // console.log(JSON.stringify(obj));
  const res = await fetch(url, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(obj),
  });
  const responseData = await res.json();
  console.log("fetch add data: ", responseData);
  return responseData;
};

export const fetchIsExistInWishlist = async (url: string) => {
  const res = await fetch(url, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
  });
  console.log("response data to check wishlist exists: ", res);
  return res.json();
};

export const fetchWalletBalance = async (url: string, token: string | null) => {
  const res = await fetch(url, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authentication: `Bearer ${token}`,
    },
  });

  return res.json();
};
